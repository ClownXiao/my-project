from django.http import HttpResponse, Http404
from django.shortcuts import render, redirect, get_object_or_404
from django.core.exceptions import ObjectDoesNotExist

from django.contrib.auth.decorators import login_required

from django.contrib.auth.models import User
from django.contrib.auth import login, authenticate
from django.core.urlresolvers import reverse
from django.core.mail import send_mail
from mimetypes import guess_type
from grumblr.models import *
from grumblr.forms import *
from django.contrib.auth.tokens import default_token_generator
from django.db import transaction
import time
current_milli_time = lambda: int(round(time.time() * 1000))

# Create your views here.
@login_required
def global_stream(request):
    blogs = Blog.objects.order_by('-time').all()
    target_user = get_object_or_404(Profileinfo, who = request.user)
    context = {'blogs': blogs,'target_user': target_user, 'timestamp': current_milli_time()}
    context['form'] = PostForm()
    return render(request, 'global_stream.html',context)


@login_required
def follow_stream(request):

    target_user = get_object_or_404(Profileinfo, who = request.user)
    following = target_user.following.all()
    follow_list = []
    for x in following:
        follow_list.append(x.who)
    blogs = Blog.objects.order_by('-time').filter(user__in = follow_list)
    if len(follow_list) == 0:
        context = {'blogs': blogs, 'target_user': target_user,'error':'error'}
    else:
        context = {'blogs': blogs,'target_user': target_user}
    context['form'] = PostForm()
    return render(request, 'follow_stream.html',context)

@transaction.atomic
@login_required
def add_blog(request):
    context = {}
    if request.method == 'GET':
        context['form']  = PostForm()
        return redirect(reverse('global_stream'))
    form = PostForm(request.POST)
    context['form'] = form
    if not form.is_valid():
        blogs = Blog.objects.order_by('-time').all()
        target_user =get_object_or_404(Profileinfo, who = request.user)
        context['blogs'] = blogs
        context['target_user'] = target_user
        return render(request, 'global_stream.html', context)
    c_location = 'Zootopia'
    new_blog = Blog(text=form.cleaned_data['text'], user=request.user, location=c_location)
    new_blog.save()
    return redirect(reverse('global_stream'))

@login_required
def profile(request,username):
    #filter return query set, use for iteration
    # "get" returns a object of model
    if len(User.objects.filter(username = username)) <= 0:
        errors = 'user does not exist'
        blogs = Blog.objects.order_by('-time').all()
        context = {'blogs': blogs,'errors':errors}
        return render(request, 'global_stream.html',context)
    else:
        target_user = User.objects.get(username = username)
        blogs = Blog.objects.order_by('-time').filter(user = target_user)
        target_user_profile = Profileinfo.objects.get(who = target_user)
        mine = get_object_or_404(Profileinfo, who = request.user)
        if mine in target_user_profile.follower.all():
            context = {'blogs': blogs, 'target_user': target_user_profile,'fans':mine}
        else:
            context = {'blogs': blogs, 'target_user':target_user_profile}
        return render(request, 'profile.html',context)

@transaction.atomic
@login_required
def edit_profile(request):

    context = {}
    target_user = get_object_or_404(Profileinfo, who = request.user)
    context['target_user'] = target_user
    profile_to_edit = get_object_or_404(Profileinfo, who = request.user)
    if request.method == 'GET':
        context['form']  = ProfileForm(instance = profile_to_edit, initial={'first_name': target_user.who.first_name,\
		  'last_name': target_user.who.last_name})
        return render(request, 'edit_profile.html',context)

    form = ProfileForm(request.POST, request.FILES, instance=profile_to_edit)

    if not form.is_valid():
        context['form'] = ProfileForm(instance=profile_to_edit)
        return render(request, 'edit_profile.html', context)

    form.save()
    target_user = get_object_or_404(Profileinfo, who=request.user)
    user = target_user.who
    user.first_name = form.cleaned_data['first_name']
    user.last_name = form.cleaned_data['last_name']
    user.save()

    return redirect(reverse('global_stream'))

@login_required
def head_image(request, userid):
    user = get_object_or_404(User, id = userid)
    profile = get_object_or_404(Profileinfo, who=user)
    if not profile.head_image:
        raise Http404
    content_type = guess_type(profile.head_image.name)
    return HttpResponse(profile.head_image, content_type = content_type)



@transaction.atomic
def register(request):
    # clean all data for debug
    #user = User.objects.all()
    #user.delete()
    #user1 = Profileinfo.objects.all()
    #user1.delete()
    #blog = Blog.objects.all()
    #blog.delete()

    # if method != post
    context = {}
    if request.method == 'GET':
        context['form']  = RegistrationForm()
        return render(request, 'register.html',context)

    form = RegistrationForm(request.POST)
    context['form'] = form
    if not form.is_valid():
        return render(request, 'register.html', context)


    new_user = User.objects.create_user(username = form.cleaned_data['username'], \
        password = form.cleaned_data['password1'], first_name = form.cleaned_data['first_name'],\
        last_name = form.cleaned_data['last_name'], email =  form.cleaned_data['email'])
    new_user.save()


    new_user = authenticate(username = form.cleaned_data['username'], \
        password = form.cleaned_data['password1'])
    new_profile = Profileinfo(who = new_user,blog_num = '0', follower_num='0', \
                              following_num= '0', head_image='img/default_head.jpeg')
    new_profile.save()
    login(request, new_user)

    token = default_token_generator.make_token(new_user)

    email_body = """
    Hey! Welcome to Grumblr!
    Help us secure your Grumblr account by verifying your email address . This lets you access all of Grumblr features.

    Http://%s%s
    """ % (request.get_host(), reverse('confirm', kwargs ={'username':new_user.username,'token':token}))

    send_mail(subject='Verify your email address',\
    message = email_body, \
    from_email = 'xiaodud@andrew.cmu,edu',\
    recipient_list = [new_user.email])

    email = 'A confirmation email has been sent to ' + new_user.email +'. Plaase click the link in that email to complete registeration'

    context['email'] = email
    return render(request, 'register_confirm.html', context)


def confirm_register(request, username, token):
    context = {}
    new_user = get_object_or_404(User, username = username)
    if not default_token_generator.check_token(new_user,token):
        context['email'] = 'authentication failed! Your account may be in danger!'
        return render(request, 'error.html', context)
    login(request, new_user)
    return redirect(reverse('global_stream'))


@login_required
def follow(request, userid):
    user = get_object_or_404(Profileinfo, id = userid)
    if len(user.follower.filter(who = request.user)) > 0:
        target_user = user.who
        blogs = Blog.objects.order_by('-time').filter(user=target_user)
        errors = 'you have already followed this user'
        context = {'blogs': blogs, 'target_user': user, 'errors':errors}
        return render(request, 'profile.html', context)
    user_self = Profileinfo.objects.get(who = request.user)
    user_self.following.add(user)
    user_self.following_num = user_self.following_num + 1
    user_self.save()

    user.follower_num = user.follower_num + 1
    user.save()
    return redirect(reverse('profile', kwargs={'username':user.who.username}))


@login_required
def unfollow(request, userid):
    user = Profileinfo.objects.get(id=userid)
    if len(user.follower.filter(who = request.user)) <= 0:
        target_user = user.who
        blogs = Blog.objects.order_by('-time').filter(user=target_user)
        errors = 'you have not followed this user yet'
        context = {'blogs': blogs, 'target_user': user, 'errors':errors}
        return render(request, 'profile.html', context)
    user_self =  get_object_or_404(Profileinfo, who = request.user)
    user_self.following.remove(user)
    user_self.following_num = user_self.following_num - 1
    user_self.save()


    user.follower_num = user.follower_num - 1
    user.save()
    return redirect(reverse('profile', kwargs={'username':user.who.username}))

# change password by previous password
@transaction.atomic
@login_required
def change_pwd(request):
    context = {}
    if request.method == 'GET':
        context['form']  = ResetPwdForm()
        return render(request, 'reset_password.html',context)

    form = ResetPwdForm(request.POST)
    context['form'] = form
    if not form.is_valid():
        return render(request, 'reset_password.html', context)

    # Here I verified user password in views because authentication using both database and auth.user library
    user = authenticate(username=request.user.username, password = form.cleaned_data['password'])
    if not user:
        context['errors'] = 'incorrect password'
        return render(request, 'reset_password.html', context)

    user = request.user
    user.set_password( form.cleaned_data['password1'])
    user.save()

    login(request, user)
    return redirect(reverse('global_stream'))


# request change password via email
def change_pwd_mail_request(request):
    context = {}
    if request.method == 'GET':
        context['form']  = ResetPwdMailForm()
        return render(request, 'reset_pwd_by_mail.html',context)

    form = ResetPwdMailForm(request.POST)
    context['form'] = form
    if not form.is_valid():
        return render(request, 'reset_pwd_by_mail.html', context)

    user = User.objects.get(username = form.cleaned_data['username'])
    token = default_token_generator.make_token(user)

    email_body = """
        You just requested to change your password.
        Click the link to complete authentication:

        Http://%s%s
        """ % (request.get_host(), reverse('reset-pwd', kwargs={'username': user.username, 'token': token}))

    send_mail(subject='Verify your email address', \
              message=email_body, \
              from_email='xiaodud@andrew.cmu,edu', \
              recipient_list=[user.email])

    email = 'A Change Password authentication email has been sent to ' + user.email + '. Plaase check your email box to complete authentication'

    context['email'] = email
    return render(request, 'register_confirm.html', context)

# change password after email authentication
def reset_pwd(request, username, token):
    context = {}
    context['username'] = username
    context['token'] = token
    user = get_object_or_404(User, username = username)
    # verify the token
    if not default_token_generator.check_token(user,token):
        context['email'] = 'authentication failed! Your account may be in danger!'
        return render(request, 'error.html', context)
    if request.method == 'GET':
        context['form'] = SetPwdForm()
        return render(request, 'set_password.html', context)

    form = SetPwdForm(request.POST)
    context['form'] = form
    if not form.is_valid():
        return render(request, 'set_password.html', context)

    user.set_password(form.cleaned_data['password1'])
    user.save()

    login(request, user)
    return redirect(reverse('global_stream'))

@transaction.atomic
def add_blogs(request):
    context = {}
    try:
        timestamp = float(request.POST['timestamp'])
    except:
        timestamp = 0.0
    form = PostForm(request.POST)
    if not form.is_valid():
        message = 'post cannot be empty or longer than 42 characters'
        context['message'] = message
        return render(request, 'blogs.json', context, content_type='application/json')
    c_location = 'Zootopia'
    new_blog = Blog(text = form.cleaned_data['text'], user = request.user, location=c_location)
    new_blog.save()
    userinfo = Profileinfo.objects.get(who=request.user)
    userinfo.blog_num += 1
    userinfo.save()
    blogs = Blog.get_changes(timestamp)
    context = {'blogs': blogs, 'timestamp': current_milli_time()}
    return render(request, 'blogs.json', context, content_type='application/json')

def refresh_blogs(request,last_time):
    print(last_time)
    timestamp = float(last_time)
    blogs = Blog.get_changes(timestamp)
    context = {'blogs': blogs, 'timestamp': current_milli_time()}
    return render(request, 'blogs.json', context, content_type='application/json')

@transaction.atomic
def add_comment(request):
    context = {}
    timestamp = current_milli_time()
    form = CommentForm(request.POST)
    if not form.is_valid():
        message = 'comment cannot be empty or longer than 50 characters'
        context['message'] = message
        context['blogid'] =  request.POST['blog-id']
        return render(request, 'comments.json', context, content_type='application/json')
    blog = Blog.objects.get(id = request.POST['blog-id'])
    new_comment = Comments(text=form.cleaned_data['text'], user=request.user, blog=blog)
    new_comment.save()
    blog.comment_num += 1
    blog.save()
    comments = Comments.get_changes(blog, timestamp)
    context = {'comments': comments, "blogid": blog.id}
    return render(request, 'comments.json', context, content_type='application/json')

def get_comments(request,blogid):
    blog = Blog.objects.get(id=blogid)
    comments = Comments.get_changes(blog, 0)
    context = {'comments': comments, "blogid": blog.id}
    return render(request, 'comments.json', context, content_type='application/json')






