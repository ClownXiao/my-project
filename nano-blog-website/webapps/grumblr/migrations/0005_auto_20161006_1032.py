# -*- coding: utf-8 -*-
# Generated by Django 1.10.2 on 2016-10-06 14:32
from __future__ import unicode_literals

from django.db import migrations, models


class Migration(migrations.Migration):

    dependencies = [
        ('grumblr', '0004_auto_20161004_1852'),
    ]

    operations = [
        migrations.AlterField(
            model_name='profileinfo',
            name='follower',
            field=models.ManyToManyField(related_name='follower_user', to='grumblr.Profileinfo'),
        ),
        migrations.AlterField(
            model_name='profileinfo',
            name='following',
            field=models.ManyToManyField(related_name='following_user', to='grumblr.Profileinfo'),
        ),
    ]