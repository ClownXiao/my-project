# -*- coding: utf-8 -*-
# Generated by Django 1.10.2 on 2016-10-04 20:36
from __future__ import unicode_literals

from django.db import migrations, models


class Migration(migrations.Migration):

    dependencies = [
        ('grumblr', '0002_auto_20161004_1616'),
    ]

    operations = [
        migrations.AlterField(
            model_name='blog',
            name='text',
            field=models.CharField(max_length=50, null=True),
        ),
    ]
