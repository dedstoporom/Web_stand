<#import "parts/common.ftlh" as c>
<@c.page>
    <div><a href="/user/profile/${user.id}">Back</a></div>
    <#list userList as userList>
        <div class="card mt-2 " style="width: 20rem;">
            <div class="card-body">
                <a href="/user/profile/${userList.id}"><span>${userList.username}</span></a>
            </div>
        </div>
    <#else>
        <div class="card mt-2 " style="width: 20rem;">
            <div class="card-body">
                <span>Empty</span>
            </div>
        </div>
    </#list>
</@c.page>