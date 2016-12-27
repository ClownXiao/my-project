/*
*	name: Xiaodu Ding
*   id: xiaodud
*
*	It's really a long and hard way to get 100 score.
*	just use explict list and LIFO, util is 53%
*	with seg list, util is 58%
*	remove footer, util is 68%
*	make the minimum block size be 16 bytes, util is 73.6%
*	with some trick and the util become 74% finally!
*	
******************************************************************************
*                                   mm.c                                     *
*           64-bit struct-based segregate free list memory allocator         *
*           ps: this comment is based on the mm-baseline.c                                                                *
*  ************************************************************************  *
*                               DOCUMENTATION                                *
*                                                                     *
*  ** STRUCTURE. **                                                          *
*                                                                            *
*  Both allocated and free blocks share the same header structure.           *
*  HEADER: 8-byte, aligned to 8th byte of an 16-byte aligned heap, where     *
*          - The lowest order bit is 1 when the block is allocated, and      *
*            0 otherwise.                                                    *
*          - The second lowest order bit is 1 when the previous block is	 *
*			 allocated, and 0 otherwise.(call it prev-allo bit)              *     
*          - The third lowest order bit is 1 when the previous block is		 *
*			 16-bytes, and 0 otherwise.(call it prev-16 bit)                 * 
*          - The whole 8-byte value with the least 3 significant bits set    *
*			 to 0 represents the size of the block as a size_t               *
*            The size of a block includes the header and footer.             *
*  Only free block have FOOTER!!!!                                           *
*  FOOTER: 8-byte, aligned to 0th byte of an 16-byte aligned heap. It        *
*          contains the copy of the block's header, but without prev-allo bit*
*		   and prev-16 bit.													 *
*  The minimum blocksize is 16 bytes.                                        *
*                                                                            *
*  Allocated blocks contain the following:                                   *
*  HEADER, as defined above.                                                 *
*  PAYLOAD: Memory allocated for program to store information.               *
*  The size of an allocated block is exactly PAYLOAD + HEADER.				 *
*                                                                            *
*  Free blocks contain the following if it's size is not 16bytes:            *
*  HEADER, as defined above.                                                 *
*  NEXT POINTER, pointer to the next free block.                             *
*  PREV POINTER, pointer to the previous free block.                         *
*  FOOTER, as defined above.                                                 *
*                                                                            *
*  Free blocks contain the following if it's size is 16bytes:			     *
*  HEADER, as defined above.                                                 *
*  NEXT POINTER, pointer to the next free block.                             *
*																			 *
*  The size of an unallocated block is at least 16 bytes.                    *
*                                                                            *
*  Block Visualization.                                                      *
*                    block     block+8          block+size					 *
*  Allocated blocks:   |  HEADER  |  ... PAYLOAD ...  |						 *
*                                                                            *
*                    block     block+8          block+size-8   block+size    *
*  Unallocated blocks: |  HEADER  |  empty or pointer |  FOOTER  |           *
*                                                                            *
*							 block     block+8          block+16			 *
*  Unallocated 16-bytes blocks: |  HEADER  |  next pointer |				 *
*                                                                            *
*  ************************************************************************  *
*  ** INITIALIZATION. **                                                     *
*                                                                            *
*  The following visualization reflects the beginning of the heap.           *
*      start            start+8           start+16                           *
*  INIT: | PROLOGUE_FOOTER | EPILOGUE_HEADER |                               *
*  PROLOGUE_FOOTER: 8-byte footer, as defined above, that simulates the      *
*                    end of an allocated block. Also serves as padding.      *
*  EPILOGUE_HEADER: 8-byte block indicating the end of the heap.             *
*                   It simulates the beginning of an allocated block         *
*                   The epilogue header is moved when the heap is extended.  *
*                                                                            *
*  ************************************************************************  *
*  ** BLOCK ALLOCATION. **                                                   *
*                                                                            *
*  Upon memory request of size S, a block of size S + wsize, rounded up to   *
*  16 bytes, is allocated on the heap, where wsize is 8.					 *
*  Selecting the block for allocation is performed by finding the first      *
*  block that can fit the content based on seglist and LIFO search policy.	 *
*		                                                                     *
*  Seg + LIFO:The search starts from the beginning of the appropriate        *
*			  freelist X, which depends on the traget size. It sequentially  *
*			  goes through each block in the segregate free list X, towards  *
*			  the end of the list X, until either                            *
*             - A sufficiently-large unallocated block is found, or          *
*             - The end of the list X is reached.                            *
*
*             In the case that the end of the list X is reached,			 *
*             the search continues from the beginning of the next freelist Y,*
*			  which is next to freelist X, sequentially going through each   *
*			  block in the list Y, until either                              *
*             - A sufficiently-large unallocated block is found, or          *
*             - The end of the last free list Z is reached, which occurs	 *
*			  when no sufficiently-large unallocated block is available.     *
*                                                                            *
*  In case that a sufficiently-large unallocated block is found, then        *
*  that block will be used for allocation. Otherwise--that is, when no       *
*  sufficiently-large unallocated block is found--then more unallocated      *
*  memory of size chunksize or requested size, whichever is larger, is       *
*  requested through mem_sbrk, and the search is redone.                     *
*                                                                            *
*  Checker:
*  My checker will check everty aspect listed int the writeup.				 *
*  I call my checker in the end of init(), malloc, free(), realloc, calloc.  *
*  If anything goes wrong, it will print error message and current heap as	 *
*  well as current free lists.												 *
*  ************************************************************************  *
*/
#include <assert.h>
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <unistd.h>
#include <stdbool.h>
#include <stdint.h>

#include "mm.h"
#include "memlib.h"

/*
 * If you want debugging output, uncomment the following.  Be sure not
 * to have debugging enabled in your final submission
 */

//#define DEBUG

#ifdef DEBUG
/* When debugging is enabled, the underlying functions get called */
#define dbg_printf(...) printf(__VA_ARGS__)
#define dbg_assert(...) assert(__VA_ARGS__)
#define dbg_requires(...) assert(__VA_ARGS__)
#define dbg_ensures(...) assert(__VA_ARGS__) 
#define dbg_checkheap(...) mm_checkheap(__VA_ARGS__)
#define dbg_printheap(...) print_heap(__VA_ARGS__)
#else
/* When debugging is disabled, no code gets generated */
#define dbg_printf(...)
#define dbg_assert(...)
#define dbg_requires(...)
#define dbg_ensures(...)
#define dbg_checkheap(...)
#define dbg_printheap(...)
#endif

/* do not change the following! */
#ifdef DRIVER
/* create aliases for driver tests */
#define malloc mm_malloc
#define free mm_free
#define realloc mm_realloc
#define calloc mm_calloc
#define memset mem_memset
#define memcpy mem_memcpy
#endif /* def DRIVER */

/* Basic constants */
typedef uint64_t word_t;
static const size_t wsize = sizeof(word_t); // word, header, footer size (bytes)
static const size_t dsize = 2 * wsize;          // double word size (bytes)
static const size_t min_block_size =  dsize; // Minimum block size, I use 16
static const size_t chunksize = (1 << 9);    // requires (chunksize % 16 == 0)

/* block in heap*/
typedef struct block
{
	/* 
	 * Header contains size + is_last_16bytes flag 
	 * + is_last_allocate flag + allocation flag 
	 */
	word_t header;
	/*
	* We don't know how big the payload will be.  Declaring it as an
	* array of size 0 allows computing its starting address using
	* pointer notation.
	*/
	char payload[0];

	/*
	* We can't declare the pointer because only free block own there areas.
	*/

	/*
	* We can't declare the footer as part of the struct, since its starting
	* position is unknown. And we don't use footer for allocated block
	* to reduce internal fragmentation.
	*/
} block_t;

/* block in free list */
typedef struct free_block 
{
	word_t * header; //exactly as the one in block_t
	struct free_block * next; 
	struct free_block * prev; //there is no prev* in 16-bytes block

} free_block_t;

/* Global variables */

/* Pointer to first block in heap*/
static block_t *heap_listp = NULL;
/* Pointer to first block in the first free list*/
static free_block_t **free_listp;

/* What is the correct alignment? */
#define ALIGNMENT 16

/* 
 * how many free lists of different size ?
 * for size n < 384, we use exactly size n as the size of class
 * othersize, we use size of log2(n) for all blocks that range 
 * from n to 2n - 1.
 * for large size that is larger than 2^10, we put them in last
 * class. so we have 384/16 + 10 = 34 diffenet free lists.
 */

/* 
 * If you want to know why I use 384 as the threshold, because
 * I tried 256 and 512, and get the same utilization. So I use 
 * (256 + 512)/2 = 384.
 */

#define FREE_N 34
#define FREE_threshold  384
 
/* rounds up to the nearest multiple of ALIrGNMENT */
static size_t align(size_t x)
{
    return ALIGNMENT * ((x+ALIGNMENT-1)/ALIGNMENT);
}

/* 
 * Calculate the most suitable free list according to the size 
 * we need. 
 * Actually I should use word "offset" instead of "index"
 * At first I use global array and use "index", then put them 
 * in the heap. And I think index is more understandable.
 */

static int get_index(size_t x) {
	if (x < FREE_threshold)
		return x/16 - 1;
	int n = 23;
	x = x >> 8;
	while (x && n<FREE_N - 1) {
		x = x >> 1;
		n = n + 1;
	}
	return n;
}


/* Function prototypes for internal helper routines */
static block_t *extend_heap(size_t size);
static void place(block_t *block, size_t asize);
static block_t *find_fit(size_t asize);
static block_t *coalesce(block_t *block);

static size_t max(size_t x, size_t y);
static word_t pack(size_t size, bool alloc);

static size_t extract_size(word_t header);
static size_t get_size(block_t *block);
static size_t get_payload_size(block_t *block);

static bool extract_alloc(word_t header);
static bool get_alloc(block_t *block);

static bool extract_last_alloc(word_t header);
static bool get_last_alloc(block_t *block);
static bool get_last_byte(block_t *block);

static void write_header(block_t *block, size_t size, 
	bool last_alloc, bool alloc, bool last_16bytes);
static void write_footer(block_t *block, size_t size, bool alloc);
static void update_last_allo(block_t *block, bool last_alloc);
static void update_16byte(block_t *block, bool last_16bytes);

static block_t *payload_to_header(void *bp);
static void *header_to_payload(block_t *block);

static block_t *find_next(block_t *block);
static word_t *find_prev_footer(block_t *block);
static block_t *find_prev(block_t *block);

bool mm_checkheap(int lineno);
void mm_print_heap(int lineno);
void mm_print_freelist(int lineno);
bool mm_check_epipro(int lineno);
bool mm_check_block(int lineno);
bool mm_check_freelist(int lineno);

static void add_freeblock(free_block_t *block, int index);
static void set_prev(free_block_t *block, free_block_t *prev);
static void set_next(free_block_t *block, free_block_t *next);
static void reconnect_freelist(free_block_t *block, int index);
static free_block_t *find_next_free(free_block_t *block);
static free_block_t *find_prev_free(free_block_t *block);
static free_block_t *find_prev_16free(free_block_t *block);


/*
* mm_init: initializes the heap; it is run once when heap_start == NULL.
*          prior to any extend_heap operation, this is the heap:
*              start            start+8           start+16
*          INIT: | PROLOGUE_FOOTER | EPILOGUE_HEADER |
* heap_listp ends up pointing to the epilogue header.
*/
bool mm_init(void)
{
	
	// Create the initial head pointer of each free list 
	free_listp = (free_block_t **)(mem_sbrk(FREE_N * wsize));
	if (free_listp == (void *)-1)
	{
		return false;
	}

	// Create the initial empty heap 
	word_t *start = (word_t *)(mem_sbrk(2 * wsize));
	if (start == (void *)-1)
	{
		return false;
	}

	start[0] = pack(0, true); // Prologue footer
	start[1] = pack(0, true) | 0x2; // Epilogue header

	// Heap starts with first block header (epilogue)
	heap_listp = (block_t *)&(start[1]);

	// Extend the empty heap with a free block of chunksize bytes
	if (extend_heap(chunksize) == NULL)
	{
		return false;
	}

	// initilize the pointer of the head of each free list 
	for (int i = 0; i < FREE_N; i++) {
		*(free_block_t **)((char *)free_listp + sizeof(wsize) * i) = NULL;
	}

	//get the offset of target free list
	int index = get_index(chunksize);
	//add the first free block to the target free list
	*(free_block_t **)((char *)free_listp + sizeof(wsize) * index) = 
		(free_block_t *)&(start[1]);
	//set the prev pointer pointing to itself(head of the target list) 
	set_prev(*(free_block_t **)((char *)free_listp + sizeof(wsize) * index),
		*(free_block_t **)((char *)free_listp + sizeof(wsize) * index));
	//set the next pointer pointing to NULL
	set_next(*(free_block_t **)((char *)free_listp + sizeof(wsize) * index)
		, NULL);
	//call checker
	dbg_checkheap(__LINE__);
	return true;
}

/*
* malloc: allocates a block with size at least (size + wsize), rounded up to
*         the nearest 16 bytes, with a minimum of dsize. Seeks a
*         sufficiently-large unallocated block on the heap to be allocated.
*         If no such block is found, extends heap by the maximum between
*         chunksize and (size + wsize) rounded up to the nearest 16 bytes,
*         and then attempts to allocate all, or a part of, that memory.
*         Returns NULL on failure, otherwise returns a pointer to such block.
*         The allocated block will not be used for further allocations until
*         freed.
*/
void *malloc(size_t size)
{
	size_t asize;      // Adjusted block size
	size_t extendsize; // Amount to extend heap if no fit is found
	block_t *block;
	void *bp = NULL;

	if (heap_listp == NULL) // Initialize heap if it isn't initialized
	{
		mm_init();
	}

	if (size == 0) // Ignore spurious request
	{
		dbg_printf("Malloc(%zd) --> %p\n", size, bp);
		return bp;
	}

	// Adjust block size to include overhead and to meet alignment requirements
	asize = align(size+wsize);

	// Search the free list for a fit
	block = find_fit(asize);

	// If no fit is found, request more memory, and then and place the block
	if (block == NULL)
	{
		extendsize = max(asize, chunksize);
		block = extend_heap(extendsize);
		if (block == NULL) // extend_heap returns an error
		{
			dbg_printf("Malloc(%zd) --> %p\n", size, bp);
			return bp;
		}
		// insert the new free block to the suitable free list
		add_freeblock((free_block_t *)block, get_index(get_size(block)));
	}
	// allocate the block to the right place
	place(block, asize);

	bp = header_to_payload(block);
	dbg_printf("Malloc(%zd) --> %p\n", size, bp);
	//call checker
	dbg_checkheap(__LINE__);
	return bp;
}

/*
* free: Frees the block such that it is no longer allocated while still
*       maintaining its size. Block will be available for use on malloc.
*/
void free(void *bp)
{
	if (bp == NULL)
	{
		return;
	}
	
	block_t *block = payload_to_header(bp);
	size_t size = get_size(block);
	//change the header and footer of the block which is goint to be freed
	write_header(block, size, get_last_alloc(block), false, get_last_byte(block));
	write_footer(block, size, false);
	
	//coalesce the new free block with it's neighbour block
	block = coalesce(block);

	//update the header of the next block, set the prev_allo bit to 0
	update_last_allo(find_next(block), false);

	//update the header of the next block, reset the prev_16byte bit 
	bool is_16byte = false;
	if (get_size(block) == 16)
		is_16byte = true;
	update_16byte(find_next(block), is_16byte);

	//add the new coalesced block to the the suitable free list
	add_freeblock((free_block_t *)block, get_index(get_size(block)));
	dbg_printf("Completed free(%p)\n", bp);
	//call checker
	dbg_checkheap(__LINE__);
}

/*
* realloc: returns a pointer to an allocated region of at least size bytes:
*          if ptrv is NULL, then call malloc(size);
*          if size == 0, then call free(ptr) and returns NULL;
*          else allocates new region of memory, copies old data to new memory,
*          and then free old block. Returns old block if realloc fails or
*          returns new pointer on success.
*/
void *realloc(void *ptr, size_t size)
{
	block_t *block = payload_to_header(ptr);
	size_t copysize;
	void *newptr;

	// If size == 0, then free block and return NULL
	if (size == 0)
	{
		free(ptr);
		return NULL;
	}

	// If ptr is NULL, then equivalent to malloc
	if (ptr == NULL)
	{
		return malloc(size);
	}

	// Otherwise, proceed with reallocation
	newptr = malloc(size);
	// If malloc fails, the original block is left untouched
	if (!newptr)
	{
		return NULL;
	}

	// Copy the old data
	copysize = get_payload_size(block); // gets size of old payload
	if (size < copysize)
	{
		copysize = size;
	}
	memcpy(newptr, ptr, copysize);

	// Free the old block
	free(ptr);
	//call checker
	dbg_checkheap(__LINE__);
	return newptr;
}

/*
* calloc: Allocates a block with size at least (elements * size + dsize)
*         through malloc, then initializes all bits in allocated memory to 0.
*         Returns NULL on failure.
*/
void *calloc(size_t nmemb, size_t size)
{
	void *bp;
	size_t asize = nmemb * size;

	if (asize / nmemb != size)
		// Multiplication overflowed
		return NULL;

	bp = malloc(asize);
	if (bp == NULL)
	{
		return NULL;
	}
	// Initialize all bits to 0
	memset(bp, 0, asize);
	//call checker
	dbg_checkheap(__LINE__);
	return bp;
}

/******** The remaining content below are helper and debug routines ********/

/*
* extend_heap: Extends the heap with the requested number of bytes, and
*              recreates epilogue header. Returns a pointer to the result of
*              coalescing the newly-created block with previous free block, if
*              applicable, or NULL in failure.
*/
static block_t *extend_heap(size_t size)
{
	void *bp;

	// Allocate an even number of words to maintain alignment
	size = align(size);
	if ((bp = mem_sbrk(size)) == (void *)-1)
	{
		return NULL;
	}

	// Initialize free block header/footer 
	block_t *block = payload_to_header(bp);
	write_header(block,size,get_last_alloc(block),false,get_last_byte(block));
	write_footer(block, size, false);
	// Create new epilogue header
	block_t *block_next = find_next(block);
	write_header(block_next, 0, false, true, false);
	// Coalesce in case the previous block was free
	return coalesce(block);
}

/* Coalesce: Coalesces current block with previous and next blocks if
*           either or both are unallocated; otherwise the block is not
*           modified. Then, insert coalesced block into the segregated list.
*           Returns pointer to the coalesced block. After coalescing, the
*           immediate contiguous previous and next blocks must be allocated.
*/
static block_t *coalesce(block_t * block)
{
	size_t size = get_size(block);

	//get the next block and get the allocation bit
	block_t *block_next = find_next(block);
	bool next_alloc = get_alloc(block_next);

	//get the previous block and get the allocation bit
	bool prev_alloc = get_last_alloc(block);
	block_t *block_prev;

	
	if (prev_alloc == true) {
		block_prev = NULL;
	}
	else {
		// case last block is 16-bytes
		if (get_last_byte(block)) {
			block_prev = (block_t *)(((char *)block) - dsize);
		}
		else {
			block_prev = find_prev(block);
		}
	}
	
	if (prev_alloc && next_alloc)              // Case 1
	{
		return block;
	}

	else if (prev_alloc && !next_alloc)        // Case 2
	{
		// remove the next free block from the corresponding free list
		reconnect_freelist((free_block_t *)block_next, 
			get_index(get_size(block_next)));

		size += get_size(block_next);
		// update the header of new coalesced block
		write_header(block, size, get_last_alloc(block), 
			false, get_last_byte(block));

		write_footer(block, size, false);
	}

	else if (!prev_alloc && next_alloc)        // Case 3
	{
		
		reconnect_freelist((free_block_t *)block_prev, 
			get_index(get_size(block_prev)));

		size += get_size(block_prev);
		write_header(block_prev, size, get_last_alloc(block_prev), 
			false, get_last_byte(block_prev));

		write_footer(block, size, false);
		block = block_prev;
	}

	else                                        // Case 4
	{
		reconnect_freelist((free_block_t *)block_next, 
			get_index(get_size(block_next)));

		reconnect_freelist((free_block_t *)block_prev, 
			get_index(get_size(block_prev)));

		size += get_size(block_next) + get_size(block_prev);
		write_header(block_prev, size, get_last_alloc(block_prev), 
			false, get_last_byte(block_prev));

		write_footer(block_next, size, false);
		block = block_prev;

	}
	return block;
}

/*
* place: Places block with size of asize at the start of bp. If the remaining
*        size is at least the minimum block size, then split the block to the
*        the allocated block and the remaining block as free, which is then
*        inserted into the segregated list. Requires that the block is
*        initially unallocated.
*/
static void place(block_t *block, size_t asize)
{
	size_t csize = get_size(block);

	if ((csize - asize) >= min_block_size)
	{
		// split the block to 2 parts
		block_t *block_next;
		// update the header of the allocated block 
		write_header(block, asize, get_last_alloc(block), 
			true, get_last_byte(block));

		// remove the initial block from the corresponding free list
		reconnect_freelist((free_block_t *)block, get_index(csize));

		bool is_16byte = false;
		if (get_size(block) == 16)
			is_16byte = true;
		block_next = find_next(block);
		// update the header and footer of the remaining free block
		write_header(block_next, csize - asize, true, false, is_16byte);
		write_footer(block_next, csize - asize, false);

		is_16byte = false;
		if (get_size(block_next) == 16)
			is_16byte = true;
		// update the header of the next block of the remaining free block
		update_16byte(find_next(block_next), is_16byte);

		/* add the remaining free block to the freelist*/
		add_freeblock((free_block_t *)block_next, 
			get_index(get_size(block_next)));
	}

	else
	{
		// remove the initial block from the corresponding free list
		reconnect_freelist((free_block_t *)block, get_index(get_size(block)));
		// update the header of the allocated block 
		write_header(block, csize, get_last_alloc(block), 
			true, get_last_byte(block));

		update_last_allo(find_next(block), true);
	}
	
}

/*
* find_fit: Looks for a free block with at least asize bytes.
*           Returns NULL if none found
*/
static block_t *find_fit(size_t asize)
{
	// LIFO
	free_block_t *block;

	// searching from the beginning of the most suitable free list
	for (int i = get_index(asize); i < FREE_N; i++) {
		// find the first block that is big enough
		for (block = *(free_block_t **)((char *)free_listp + sizeof(wsize) * i);
		block != NULL; block = find_next_free(block)){
			if (asize <= get_size((block_t *)block)){
				return (block_t *)block;
			}
		}
	}
	return NULL; // no fit found
}



/*
* max: returns x if x > y, and y otherwise.
*/
static size_t max(size_t x, size_t y)
{
	return (x > y) ? x : y;
}



/*
* pack: returns a header reflecting a specified size and its alloc status.
*       If the block is allocated, the lowest bit is set to 1, and 0 otherwise.
*/
static word_t pack(size_t size, bool alloc)
{
	return alloc ? (size | 1) : size;
}


/*
* extract_size: returns the size of a given header value based on the header
*               specification above.
*/
static size_t extract_size(word_t word)
{
	return (word & ~(word_t)0xF);
}

/*
* get_size: returns the size of a given block by clearing the lowest 4 bits
*           (as the heap is 16-byte aligned).
*/
static size_t get_size(block_t *block)
{
	return extract_size(block->header);
}

/*
* get_payload_size: returns the payload size of a given block, equal to
*                   the entire block size minus the header and footer sizes.
*/
static word_t get_payload_size(block_t *block)
{
	size_t asize = get_size(block);
	return asize - wsize;
}

/*
* extract_alloc: returns the allocation status of a given header value based
*                on the header specification above.
*/
static bool extract_alloc(word_t word)
{
	return (bool)(word & 0x1);
}

/*
* extract_last_alloc: returns the prev_allocation status of a given header 
*					  value based on the header specification above.
*/
static bool extract_last_alloc(word_t word)
{
	return (bool)(word & 0x2);
}

/*
* get_alloc: returns true when the block is allocated based on the
*            block header's lowest bit, and false otherwise.
*/
static bool get_alloc(block_t *block)
{
	return extract_alloc(block->header);
}

/*
* get_last_alloc: returns true when the last block is allocated based on the
*				  block header's second lowest bit, and false otherwise.
*/
static bool get_last_alloc(block_t *block)
{
	return extract_last_alloc(block->header);
}

/*
* get_last_byte: returns true when the last block is 16-bytes based on the
*				  block header's third lowest bit, and false otherwise.
*/
static bool get_last_byte(block_t *block)
{
	return (bool)((block->header) & 0x4);
}

/*
* write_header: given a block and its size and allocation status,
*               writes an appropriate value to the block header.
*/
static void write_header(block_t *block, size_t size, bool last_alloc, 
	bool alloc, bool last_16bytes)
{
	block->header = pack(size, alloc);
	update_last_allo(block, last_alloc);
	update_16byte(block, last_16bytes);
}

/*
* update_last_allo: given a block, if the previous block is allocated,
*                   set prev_allo bit to 1, otherwise 0.
*/
static void update_last_allo(block_t *block, bool last_alloc)
{
	if(last_alloc)
		block->header = (block->header) | 0x2 ;
	else
		block->header = (block->header) & (~0x2);
}

/*
* update_last_allo: given a block, if the previous block is 16-bytes,
*				    set prev_16byte bit to 1, otherwise 0.
*/
static void update_16byte(block_t *block, bool last_16byte)
{
	if (last_16byte)
		block->header = (block->header) | 0x4;
	else
		block->header = (block->header) & (~0x4);
}

/*
* write_footer: given a block and its size and allocation status,
*               writes an appropriate value to the block footer by first
*               computing the position of the footer.
*/
static void write_footer(block_t *block, size_t size, bool alloc)
{
	word_t *footerp = (word_t *)((block->payload) + get_size(block) - dsize);
	*footerp = pack(size, alloc);
}


/*
* find_next: returns the next consecutive block on the heap by adding the
*            size of the block.
*/
static block_t *find_next(block_t *block)
{
	return (block_t *)(((char *)block) + get_size(block));
}

/*
* find_next_free: returns the next consecutive block in the free list 
*/
static free_block_t *find_next_free(free_block_t *block)
{
	return block->next;
}

/*
* find_prev_free: returns the previous consecutive block in the free list
*/
static free_block_t *find_prev_free(free_block_t *block)
{
	return block->prev;
}

/*
* find_prev_16free: returns the previous consecutive block, which is 
*					16-bytes, in the 16-byte size free list.
*				    Because we don't define "prev pointer" in 16-bytes 
*					block, we need to traverse the whole 16-byte size list
*/
static free_block_t *find_prev_16free(free_block_t *block)
{
	free_block_t *temp;
	for (temp = *free_listp; temp != NULL;temp = find_next_free(temp)) {
		if (temp->next == block) {
			return temp;
		}
	}
	return NULL;
}


/*
* find_prev_footer: returns the footer of the previous block.
*/
static word_t *find_prev_footer(block_t *block)
{
	// Compute previous footer position as one word before the header
	return (&(block->header)) - 1;
}

/*
* find_prev: returns the previous block position by checking the previous
*            block's footer and calculating the start of the previous block
*            based on its size.
*/
static block_t *find_prev(block_t *block)
{
	word_t *footerp = find_prev_footer(block);
	size_t size = extract_size(*footerp);
	return (block_t *)((char *)block - size);
}

/*
* payload_to_header: given a payload pointer, returns a pointer to the
*                    corresponding block.
*/
static block_t *payload_to_header(void *bp)
{
	return (block_t *)(((char *)bp) - offsetof(block_t, payload));
}

/*
* header_to_payload: given a block pointer, returns a pointer to the
*                    corresponding payload.
*/
static void *header_to_payload(block_t *block)
{
	return (void *)(block->payload);
}

/*
* add_freeblock: add a new free block to the suitable free list
*/
static void add_freeblock(free_block_t *block, int index) 
{
	// save the current first block in the list
	free_block_t *temp = *(free_block_t **)((char *)free_listp 
		+ sizeof(wsize) * index);
	// make the header of the list point to the new block
	*(free_block_t **)((char *)free_listp + sizeof(wsize) * index) = block;

	// if the block size is 16 bytes, it has no prev pointer
	if (index != 0) 
	{
		if (temp != NULL)
			set_prev(temp, block);

		set_prev(block, *(free_block_t **)((char *)free_listp 
			+ sizeof(wsize) * index));
	}
	set_next(block, temp);
}

/*
* set_prev: set the prev pointer pointing to block "prev"
*/
static void set_prev(free_block_t *block, free_block_t *prev) 
{
	block->prev = prev;
}

/*
* set_prev: set the next pointer pointing to block "next"
*/
static void set_next(free_block_t *block, free_block_t *next) 
{
	block->next = next;
}

/*
* reconnect_freelist: occur when a free block is going to be removed.
*					  remove the free block and reconnect the neighbor blocks  
*/
static void reconnect_freelist(free_block_t *block, int index) 
{
	// case 16 bytes block 
	if (index == 0) 
	{
		free_block_t *next_free = find_next_free(block);
		if (block == *free_listp) 
		{
			// if the removed block is the first in the list
			(*free_listp) = next_free;
		}
		else {
			//if the removed block is not the first in the list, we need to
			//traverse the list to find it's previous block
			free_block_t *prev_free = find_prev_16free(block);
			set_next(prev_free, next_free);
		}
	}
	// case non-16 bytes block 
	else {
		free_block_t *next_free = find_next_free(block);
		free_block_t *prev_free = find_prev_free(block);
		if (block == *(free_block_t **)((char *)free_listp 
			+ sizeof(wsize) * index)) 
		{
			// if the removed block is the first in the list
			*(free_block_t **)((char *)free_listp + 
				sizeof(wsize) * index) = next_free;
		}
		else {
			set_next(prev_free, next_free);
		}
		if (next_free != NULL)
			set_prev(next_free, prev_free);
	}
}

/*
 * Return whether the pointer is in the heap.
 * May be useful for debugging.
 */
static bool in_heap(const void *p) {
    return p <= mem_heap_hi() && p >= mem_heap_lo();
}

/*
 * Return whether the pointer is aligned.
 * May be useful for debugging.
 */
static bool aligned(const void *p) {
    size_t ip = (size_t) p;
    return align(ip) == ip;
}

/* mm_checkheap: checks the heap for correctness; returns true if
*               the heap is correct, and false otherwise.
*               can call this function using mm_checkheap(__LINE__);
*               to identify the line number of the call site.
*/
bool mm_checkheap(int lineno) {
	bool flag = true;
	if (!mm_check_epipro)
		flag =  false;
	if (!mm_check_block)
		flag = false;
	if (!mm_check_freelist)
		flag = false;
	// if anything goes wrong, print the current heap and free list to debug
	if (!flag)
	{
		mm_print_heap(lineno);
		mm_print_freelist(lineno);
	}
    return flag;
}

//print heap
void mm_print_heap(int lineno) {
	block_t *temp = heap_listp;
	printf("********************heap_start*********************\n");
	for (temp = heap_listp; get_size(temp) > 0; temp = find_next(temp)) {

		printf("header: %zd\t size:%zd allo:%d\t addr:%p\n", temp->header, 
			get_size(temp), get_alloc(temp), header_to_payload(temp));
		//allocated block and 16-bytes block has no footer
		if (!get_alloc(temp) && get_size(temp) != 16)
			printf("footer: %zd\t size:%zd allo:%d\n", *(word_t*)((char *)temp + 
				get_size(temp) - wsize), get_size(temp), get_alloc(temp));
		else
			printf("footer: N/A\n");
	}
	printf("********************heap_end*********************\n");
}

//print free lists
void mm_print_freelist(int lineno) {
	free_block_t *temp;
	printf("---------------------freelist_start---------------------\n");
	for (int i = 0; i < FREE_N; i++) {
		
		for (temp = *(free_block_t **)((char *)free_listp + sizeof(wsize) * i);
			temp != NULL; temp = find_next_free(temp)) {

			printf("freelistNo. %d\theader: %zd\t addr:%p\n", i, *(word_t*)temp, 
				header_to_payload((block_t *)temp));
		}
	}
	printf("---------------------freelist_end---------------------\n");
}

//check epilogue and prologue blocks.
bool mm_check_epipro(int lineno)
{
	word_t *pro = (word_t *)payload_to_header((void*)heap_listp);
	word_t *epi = (word_t *)((char *)mem_heap_hi() - 7);
	size_t size = mem_heapsize();
	//check if epilogues and prologues correspond to the heap size
	if ((word_t)epi - (word_t)pro + FREE_N * sizeof(wsize) != size - 8)
	{
		printf("\nerror happened in line: %d : prologue and \
epilogue doesn't match!\n", lineno);
		printf("detail:\tprologue address: %p\n\tepilogue address: \
%p\n\theapsize:\t0x%lx", pro, epi, size - FREE_N * sizeof(wsize));
		return false;
	}
	
	//check prologue
	if (*pro != 1)
	{
		printf("\nerror happened in line: %d : wrong content in prologue. \
\ndetail:\t content in prologue:\t0x%lx", lineno,*pro);
		return false;
	}

	//check epilogue
	if (((*epi) & (~0x6)) != 1)
	{
		printf("\nerror happened in line: %d : wrong content in epilogue. \
\ndetail:\t content in prologue:\t0x%lx", lineno, *epi);
		return false;
	}
	return true;
}

/*
 *check every aspects listed in writeup about heap
 */
bool mm_check_block(int lineno)
{
	block_t *temp;
	for (temp = heap_listp; get_size(temp) > 0; temp = find_next(temp)) 
	{
		//check if the address is aligned
		if (!aligned((void *)temp)) 
		{
			printf("\nerror happened in line: %d : pointer %p is not \
aligned.\n", lineno, temp);
			return false;
		}
		//check if block size is aligned
		if (get_size(temp) % ALIGNMENT != 0) 
		{
			printf("\nerror happened in line: %d : block starting at %p \
is not aligned.\n", lineno, temp);
			return false;
		}
		//check if block size is too small
		if (get_size(temp) < min_block_size ) 
		{
			printf("\nerror happened in line: %d : block starting at %p is \
smaller than the minimum block size.\n", lineno, temp);
			return false;
		}
		//check free block's header and footer
		if (!get_alloc(temp))
		{
			if (get_size(temp) != 16)// only no-16bytes free block has footer
			{
				if ((temp->header & ~0x6) != (word_t)((temp->payload) + 
					get_size(temp) - dsize))
				{
					printf("\nerror happened in line: %d : header and footer of \
block, which starting at %p, doesn't match!\n", lineno, temp);
					return false;
				}
			}
			
		}
		//check if there are two consecutive free blocks in the heap.
		if (!get_alloc(temp))
		{
			//get the next block and get the allocation bit
			block_t *block_next = find_next(temp);
			bool next_alloc = get_alloc(block_next);
			//get the previous block and get the allocation bit
			bool prev_alloc = get_last_alloc(temp);

			if (next_alloc || prev_alloc)
			{
				printf("\nerror happened in line: %d :there are two consecutive \
free blocks around %p in the heap.\n", lineno, temp);
				return false;
			}
		}
	}
	return true;
}

/*
*check every aspect listed in writeup about free list
*/
bool mm_check_freelist(int lineno)
{
	free_block_t *temp; 
	free_block_t *next_free;
	int count_freelist = 0;
	int count_heap = 0;
	for (int i = 0; i < FREE_N; i++) 
	{
		for (temp = *(free_block_t **)((char *)free_listp + sizeof(wsize) * i); 
			temp != NULL; temp = find_next_free(temp)) 
		{
			count_freelist++;//count free blocks in free lists
			//check if all next/previous pointers are consistent
			if (i != 0)
			{
				next_free = find_next_free(temp);
				if ((next_free != NULL) && (next_free->prev != temp))
				{
					printf("\nerror happened in line: %d :next/previous pointers of \
block starting at : %p are consistent.\n", lineno, temp);
					return false;
				}
			}
			// check if all free list pointers are between heap range
			if (!in_heap((void *)temp))
			{
				printf("\nerror happened in line: %d :free lists pointer: %p is \
not in the heap.\n", lineno, temp);
				return false;
			}
			/*check if all blocks in each list bucket fall 
			 *within bucket size range (segregated list).
			 */
			if (get_index(get_size((block_t *)temp)) != i)
			{
				printf("\nerror happened in line: %d :block starting at: %p \
 doesn't fall within bucket size range .\n", lineno, temp);
				return false;
			}
		}
	}
	//count free blocks in the heap
	block_t * tempb;
	for (tempb = heap_listp; get_size(tempb) > 0; tempb = find_next(tempb))
	{
		if (!get_alloc(tempb))
		{
			count_heap++;
		}
	}
	//check if free blocks in the heap are equal to free blocks in the free lists
	if (count_heap != count_freelist)
	{
		printf("\nerror happened in line: %d :free blocks in the heap aren't \
equal to free blocks in the free lists.\n", lineno);
		return false;
	}
	return true;
}



