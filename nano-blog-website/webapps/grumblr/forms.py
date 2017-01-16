from django import forms

from django.contrib.auth.models import User
from grumblr.models import  *

class RegistrationForm(forms.Form):
    first_name = forms.CharField(max_length=20)
    last_name = forms.CharField(max_length=20)
    username = forms.CharField(max_length = 20)
    email = forms.EmailField(max_length = 40)
    password1 = forms.CharField(max_length = 200,
                                label='Password',
                                widget = forms.PasswordInput())
    password2 = forms.CharField(max_length = 200,
                                label='Confirm password',
                                widget = forms.PasswordInput())


    # Customizes form validation for properties that apply to more
    # than one field.  Overrides the forms.Form.clean function.
    def clean(self):
        # Calls our parent (forms.Form) .clean function, gets a dictionary
        # of cleaned data as a result
        cleaned_data = super(RegistrationForm, self).clean()

        # Confirms that the two password fields match
        password1 = cleaned_data.get('password1')
        password2 = cleaned_data.get('password2')
        if password1 and password2 and password1 != password2:
            raise forms.ValidationError("Passwords did not match.")

        # Generally return the cleaned data we got from our parent.
        return cleaned_data


    # Customizes form validation for the username field.
    def clean_username(self):
        # Confirms that the username is not already present in the
        # User model database.
        username = self.cleaned_data.get('username')
        if User.objects.filter(username__exact=username):
            raise forms.ValidationError("Username is already taken.")

        # Generally return the cleaned data we got from the cleaned_data
        # dictionary
        return username


class ProfileForm(forms.ModelForm):
    first_name = forms.CharField(max_length=20)
    last_name = forms.CharField(max_length=20)
    class Meta:
        model = Profileinfo
        fields = ('age', 'location', 'marriage',\
                  'short_bio', 'head_image')
        widgets = {'head_image': forms.FileInput()}

class ResetPwdForm(forms.Form):
    password = forms.CharField(max_length = 200,
                                label='Current Password',
                                widget = forms.PasswordInput())
    password1 = forms.CharField(max_length = 200,
                                label='Password',
                                widget = forms.PasswordInput())
    password2 = forms.CharField(max_length = 200,
                                label='Confirm password',
                                widget = forms.PasswordInput())

    def clean(self):
        # Calls our parent (forms.Form) .clean function, gets a dictionary
        # of cleaned data as a result
        cleaned_data = super(ResetPwdForm, self).clean()

        # Confirms that the two password fields match
        password1 = cleaned_data.get('password1')
        password2 = cleaned_data.get('password2')
        if password1 and password2 and password1 != password2:
            raise forms.ValidationError("Passwords did not match.")

        # Generally return the cleaned data we got from our parent.
        return cleaned_data



class PostForm(forms.Form):
        text = forms.CharField(max_length=42)

        def clean_text(self):
            text = self.cleaned_data.get('text')
            if len(text) > 42 :
                raise forms.ValidationError("Blog can not be longer than 42 characters.")
            return text


class CommentForm(forms.Form):
    text = forms.CharField(max_length=50)

    def clean_text(self):
        text = self.cleaned_data.get('text')
        if len(text) > 50:
            raise forms.ValidationError("comment can not be longer than 50 characters.")
        return text

class ResetPwdMailForm(forms.Form):
    username = forms.CharField(max_length=20)
    email = forms.EmailField(max_length=40)

    def clean(self):
        # Calls our parent (forms.Form) .clean function, gets a dictionary
        # of cleaned data as a result
        cleaned_data = super(ResetPwdMailForm, self).clean()
        username = self.cleaned_data.get('username')
        try:
            user = User.objects.get(username__exact=username)
        except:
            raise forms.ValidationError("Username did not exist.")
        if user.email != cleaned_data.get('email'):
            raise forms.ValidationError("Email address and username did not match")

        # Generally return the cleaned data we got from our parent.
        return cleaned_data


class SetPwdForm(forms.Form):
    password1 = forms.CharField(max_length=200,
                                label='Password',
                                widget=forms.PasswordInput())
    password2 = forms.CharField(max_length=200,
                                label='Confirm password',
                                widget=forms.PasswordInput())

    def clean(self):
        # Calls our parent (forms.Form) .clean function, gets a dictionary
        # of cleaned data as a result
        cleaned_data = super(SetPwdForm, self).clean()

        # Confirms that the two password fields match
        password1 = cleaned_data.get('password1')
        password2 = cleaned_data.get('password2')
        if password1 and password2 and password1 != password2:
            raise forms.ValidationError("Passwords did not match.")

        # Generally return the cleaned data we got from our parent.
        return cleaned_data








