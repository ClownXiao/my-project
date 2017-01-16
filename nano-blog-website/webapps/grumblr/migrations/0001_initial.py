# -*- coding: utf-8 -*-
# Generated by Django 1.10.1 on 2016-09-30 06:09
from __future__ import unicode_literals

from django.conf import settings
from django.db import migrations, models
import django.db.models.deletion


class Migration(migrations.Migration):

    initial = True

    dependencies = [
        migrations.swappable_dependency(settings.AUTH_USER_MODEL),
    ]

    operations = [
        migrations.CreateModel(
            name='Blog',
            fields=[
                ('id', models.AutoField(auto_created=True, primary_key=True, serialize=False, verbose_name='ID')),
                ('text', models.CharField(max_length=50)),
                ('location', models.CharField(max_length=50)),
                ('time', models.DateTimeField(auto_now_add=True)),
                ('user', models.ForeignKey(on_delete=django.db.models.deletion.CASCADE, to=settings.AUTH_USER_MODEL)),
            ],
        ),
        migrations.CreateModel(
            name='Profileinfo',
            fields=[
                ('id', models.AutoField(auto_created=True, primary_key=True, serialize=False, verbose_name='ID')),
                ('first_name', models.CharField(blank='true', max_length=50)),
                ('last_name', models.CharField(blank='true', max_length=50)),
                ('following_num', models.IntegerField(default=0)),
                ('follower_num', models.IntegerField(default=0)),
                ('blog_num', models.IntegerField(default=0)),
                ('head_image', models.ImageField(blank='true', upload_to='img')),
                ('short_bio', models.CharField(blank='true', max_length=420)),
                ('age', models.IntegerField(blank='true', default=0)),
                ('location', models.CharField(blank='true', default='secret', max_length=100)),
                ('marriage', models.CharField(blank='true', default='secret', max_length=20)),
                ('follower', models.ManyToManyField(related_name='_profileinfo_follower_+', to='grumblr.Profileinfo')),
                ('following', models.ManyToManyField(related_name='_profileinfo_following_+', to='grumblr.Profileinfo')),
                ('who', models.OneToOneField(on_delete=django.db.models.deletion.CASCADE, to=settings.AUTH_USER_MODEL)),
            ],
        ),
    ]
