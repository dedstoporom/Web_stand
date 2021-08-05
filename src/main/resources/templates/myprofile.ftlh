<#import "parts/common.ftlh" as c>
<@c.page>
    <div><h1>My Profile</h1></div>
    <div class="mt-2"><h2>${user.username}</h2></div>
    <div><a href="/main">Back</a></div>
    <form method="post"> <#--без action тк отправляется туда же откуда и пришла-->
        <div class="form-group row">
            <label class="col-sm-1.5 col-form-label">New E-mail</label>
            <div class="col-sm-4">
                <input type="email" class="form-control" name="email"/>
            </div>
        </div>
        <div class="form-group row">
            <label class="col-sm-1.5 col-form-label">New password</label>
            <div class="col-sm-4">
                <input type="password" class="form-control" name="password"/>
            </div>
        </div>
        <div class="mt-4">
        <input type="submit" name="save_profile" value="Save"/>
        </div>
        <input type="hidden" name="_csrf" value="${_csrf.token}" />
    </form>
</@c.page>
