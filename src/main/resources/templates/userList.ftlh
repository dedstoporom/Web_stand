<#import "parts/common.ftlh" as c>
<@c.page>
    <a href="/main">Back</a>
    <table>
    <tbody>
    <tr>
        <th>Username</th>
        <th>Role</th>
    </tr>
    <#list user_list as user_list>
    <tr>
        <td><span>${user_list.username}</span></td>
        <td>
        <#list user_list.roles as role>
            <span>${role}<#sep></span>
        </#list>
        </td>
        <td><a href="/user/${user_list.id}">Edit</a></td>
    </tr>
    </#list>
    </tbody>
</table>
</@c.page>