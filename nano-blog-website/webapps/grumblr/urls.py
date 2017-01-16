"""webapps URL Configuration

The `urlpatterns` list routes URLs to views. For more information please see:
    https://docs.djangoproject.com/en/1.10/topics/http/urls/
Examples:
Function views
    1. Add an import:  from my_app import views
    2. Add a URL to urlpatterns:  url(r'^$', views.home, name='home')
Class-based views
    1. Add an import:  from other_app.views import Home
    2. Add a URL to urlpatterns:  url(r'^$', Home.as_view(), name='home')
Including another URLconf
    1. Import the include() function: from django.conf.urls import url, include
    2. Add a URL to urlpatterns:  url(r'^blog/', include('blog.urls'))
"""
from django.conf.urls import url,include
from django.contrib import admin
import  grumblr.views
#import django.contrib.auth.views
from django.contrib.auth import views as auth_views
from django.views.generic import TemplateView

urlpatterns = [
    url(r'^admin/', admin.site.urls),
    url(r'^$', grumblr.views.global_stream, name = 'global_stream'),
    url(r'^profile/(?P<username>.*)$', grumblr.views.profile, name = 'profile'),
    url(r'^register$', grumblr.views.register, name = "register"),
    url(r'^add-blog$', grumblr.views.add_blog, name = "add-blog"),
    url(r'^add-blogs$', grumblr.views.add_blogs, name = "add-blogs"),
    url(r'^edit-profile', grumblr.views.edit_profile, name = "edit-profile"),
    url(r'^head-image/(?P<userid>\d+)$', grumblr.views.head_image, name = 'head-image'),
    url(r'login$',auth_views.login,{'template_name':'grumblr/login.html', 'redirect_authenticated_user': True}, name = 'login'),
    url(r'logout$',auth_views.logout_then_login, name = 'logout'),
    url(r'^follow/(?P<userid>\d+)$', grumblr.views.follow, name = 'follow'),
    url(r'^unfollow/(?P<userid>\d+)$', grumblr.views.unfollow, name = 'unfollow'),
    url(r'^follow_stream$', grumblr.views.follow_stream, name = 'follow-stream'),
    url(r'^change_pwd$', grumblr.views.change_pwd, name = 'change-pwd'),
    url(r'^change_pwd_by_mail/(?P<username>.+)/(?P<token>.+)$', grumblr.views.reset_pwd, name = 'reset-pwd'),
    url(r'^confirm-register/(?P<username>.+)/(?P<token>.+)$', grumblr.views.confirm_register, name = 'confirm'),
    url(r'^reset_pwd_by_mail$', grumblr.views.change_pwd_mail_request, name = 'reset_pwd_by_mail'),
    url(r'^refresh-blogs/(?P<last_time>.+)$', grumblr.views.refresh_blogs),
    url(r'^add-comment$', grumblr.views.add_comment),
    url(r'^get-comments/(?P<blogid>\d+)$', grumblr.views.get_comments)

]
