# -*- coding: utf-8 -*-
# Generated by Django 1.10.2 on 2016-10-04 22:52
from __future__ import unicode_literals

from django.db import migrations, models


class Migration(migrations.Migration):

    dependencies = [
        ('grumblr', '0003_auto_20161004_1636'),
    ]

    operations = [
        migrations.AlterField(
            model_name='blog',
            name='text',
            field=models.CharField(default=' ', max_length=50),
        ),
    ]
