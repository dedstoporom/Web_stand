<#macro page>
<!DOCTYPE html>
<html lang="en">
<head>
<#--<link rel="stylesheet" href="/static/style.css"/>-->
    <meta charset="UTF-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1"/>
    <!-- Bootstrap CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.1/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-+0n0xVW2eSR5OomGNYDnhzAbDsOXxcvSN1TPprVMTNDbiYZCxYbOOl7+AMvyTG2x" crossorigin="anonymous"/>
    <script src="https://kit.fontawesome.com/646094f534.js" crossorigin="anonymous"></script>
    <script src="https://www.google.com/recaptcha/api.js"></script>

    <script src="https://cdnjs.cloudflare.com/ajax/libs/turbolinks/5.2.0/turbolinks.js" integrity="sha512-G3jAqT2eM4MMkLMyQR5YBhvN5/Da3IG6kqgYqU9zlIH4+2a+GuMdLb5Kpxy6ItMdCfgaKlo2XFhI0dHtMJjoRw==" crossorigin="anonymous" referrerpolicy="no-referrer"></script>

    <script defer src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.9.2/dist/umd/popper.min.js" integrity="sha384-IQsoLXl5PILFhosVNubq5LC7Qb9DXgDA9i+tQ8Zj3iwWAwPtgFTxbJ8NT4GN1R8p" crossorigin="anonymous"></script>

</head>
<body>
<#include "navbar.ftlh">
<div class="container mt-3">
<#nested>
</div>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.1/dist/js/bootstrap.min.js" integrity="sha384-Atwg2Pkwv9vp0ygtn1JAojH0nYbwNJLPhwyoVbhoPwBhjQPR5VtM2+xf0Uwh9KtT" crossorigin="anonymous"></script>
<#--<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.1/dist/js/bootstrap.bundle.min.js" integrity="sha384-gtEjrD/SeCtmISkJkNUaaKMoLD0//ElJ19smozuHV6z3Iehds+3Ulb9Bn9Plx0x4" crossorigin="anonymous"></script>-->

</body>
</html>
</#macro>
<#macro user_page path isRegisterForm> <#---переменная login c арг path, который вставляется уже при объявлении--->
    <form action="${path}" method="post">
        <div class="form-group row">
            <#if usernameError??>
                ${usernameError}
            </#if>
            <label class="col-sm-1.5 col-form-label">User Name</label>
            <div class="col-sm-4">
                <input type="text" class="form-control" name="username"/>
            </div>
        </div>
        <#if isRegisterForm>
            <div class="form-group row">
                <#if emailError??>
                    ${emailError}
                </#if>
                <label class="col-sm-1.5 col-form-label">E-mail</label>
                <div class="col-sm-4">
                    <input type="email" class="form-control" name="email"/>
                </div>
            </div>
        </#if>
        <div class="form-group row">
            <#if passwordError??>
                ${passwordError}
            </#if>
            <label class="col-sm-1.5 col-form-label">Password</label>
            <div class="col-sm-4">
                <input type="password" class="form-control" name="password"/>
            </div>
        </div>
        <input type="hidden" name="_csrf" value="${_csrf.token}"/>
        <#if isRegisterForm>
            <div class="mt-3">
                <#if captchaError??>
                    ${captchaError}
                </#if>
                <div class="g-recaptcha" data-sitekey="6LcHyC4bAAAAABYxvi6dSJxSpZ26HXs-tUmavdSS"></div>
            </div>
        </#if>
        <div class="mt-3">
                <input type="submit" value="<#if !isRegisterForm>Sign in <#else>Registration</#if>"/>
        </div>
    </form>
    <div class="mt-3">
    <#if !isRegisterForm>
    <a href="/registration">Registration</a>
    </#if>
    </div>
</#macro>
