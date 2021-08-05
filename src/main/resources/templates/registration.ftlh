<#import "parts/common.ftlh" as c>
<@c.page>
    <div><a href="/main">Back</a></div>
    ${messages?ifExists}
    <@c.user_page "/registration" true>
    </@c.user_page>
</@c.page>