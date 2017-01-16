from django.db import models
from django.utils import timezone
import datetime

# Create your models here.

from django.contrib.auth.models import User

class Blog(models.Model):
    text = models.CharField( max_length = 50,default= ' ')
    user = models.ForeignKey(User)
    location = models.CharField(max_length = 50)
    time = models.DateTimeField(auto_now_add = True, auto_now=False)
    repost_num = models.IntegerField(default = 0)
    like_num = models.IntegerField(default = 0)
    comment_num = models.IntegerField(default = 0)
    blog_image =  models.ImageField(upload_to = 'post-img',blank = 'true')



    def __str__(self):
        return self.text

    @staticmethod
    def get_changes(timestamp=0):
        t = datetime.datetime.fromtimestamp(timestamp/1000.0)
        return Blog.objects.filter(time__gt=t).distinct()

    @property
    def html(self):
        x =  "\
         <div class = 'blog-item'>\
          <img   alt = 'small-head-img' class = 'small-header' src = '/head-image/%s '>\
                <p class = 'bloger-name'>\
                    <a class = 'name-link' href = '/profile/%s'>%s %s </a>\
                    <small class = 'blog-location'>\
                        <span class='glyphicon glyphicon-map-marker'></span>\
                        %s\
                    </small>\
                </p>\
                <p class = 'blog-time'> %s from iphone</p>\
                <p class = 'blog-body'> %s</p>\
                <hr class = 'cut-comment'>\
                    <div id='table-comment%s'>\
                <table >\
                  <tr>\
                  <th>\
                    <a href='#'  class = 'comment comment-noborder'>\
                    <span class='glyphicon glyphicon-share'> 451</span>\
                    </a>\
                  </th>\
                  <th id=%s class = 'comment-click'>\
                    <a class = 'comment' >\
                    <span class='glyphicon glyphicon-comment'> %s</span>\
                    </a>\
                  </th>\
                  <th>\
                    <a href='#' class = 'comment' >\
                    <span class='glyphicon glyphicon-thumbs-up'> 521</span>\
                    </a>\
                  </th>\
                </tr>\
                </table>\
                    </div>\
                <hr class = 'cut-line' >\
              </div><!-- blog-item -->\
        "%( self.user.id, self.user.username, self.user.first_name, self.user.last_name,  self.location,  self.time.astimezone().strftime("%b. %d, %Y, %I:%M %p"), self.text, self.id,self.id, self.comment_num)

        return x

class Profileinfo(models.Model):
    who = models.OneToOneField(User)
    following = models.ManyToManyField('self', related_name='follower',symmetrical = False)
    following_num = models.IntegerField(default = 0)
    follower_num = models.IntegerField(default = 0)
    blog_num = models.IntegerField(default = 0)
    head_image = models.ImageField(upload_to = 'img',blank = 'true')
    short_bio = models.CharField(max_length = 420,blank = 'true')
    age = models.IntegerField(default = 0, blank = 'true')
    location = models.CharField(max_length = 100,default = 'secret', blank = 'true')
    marriage = models.CharField(max_length = 20,default= 'secret', blank = 'true')

    def __unicode__(self):
        return "%s %s" % (self.who.first_name, self.who.last_name)

    @staticmethod
    def get_profile(who):
        return Profileinfo.objects.get(who = who)

class Comments(models.Model):
    user = models.ForeignKey(User)
    text = models.CharField(max_length = 100)
    blog = models.ForeignKey(Blog)
    last_modified = models.DateTimeField(default=timezone.now)

    def __str__(self):
        return self.text

    @staticmethod
    def get_changes(blog, timestamp=0):
        t = datetime.datetime.fromtimestamp(timestamp / 1000.0)
        return Comments.objects.filter(last_modified__gt=t, blog=blog).distinct()


    @property
    def html(self):
        print(timezone.now())
        x = "<div class = 'comment-item'>\
        <img   alt = 'small-head-img' class = 'smaller-header ' src = '/head-image/%s '>\
                <p class = 'comment-name'> %s</p> \
            <p class = 'comment-text'> %s</p>\
                    <p class = 'comment-time'> %s </p> \
                <hr class = 'comment-hr'>\
        </div>"%(self.user.id, self.user.first_name + self.user.last_name,self.text,self.last_modified.astimezone().strftime("%b. %d, %Y, %I:%M %p"))
        return x



