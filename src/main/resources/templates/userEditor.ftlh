<#import "parts/common.ftlh" as c>
<@c.page>
    <div>User Editor</div>
    <a href="/main">Back</a>
    <form action="/user" method="post">
        <input type="text" name="username" value="${user.username}"/>
        <#list roles as roles>
            <div>
                <label><input type="checkbox" name="${roles}" ${user.roles?seq_contains(roles)?string("cheked","")}>${roles}</label>
            </div>
        </#list>
        <input type="hidden" value="${user.id}" name="userId"/>
        <input type="hidden" name="_csrf" value="${_csrf.token}" />
<#--        <button type="submit">Save</button>-->
    </form>
</@c.page>