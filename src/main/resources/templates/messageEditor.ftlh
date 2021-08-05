<#import "parts/common.ftlh" as c>
<@c.page>
    <div><a href="/main">Back</a></div>
    <div class="mt-3">
        <form action="/message/@{message}" method="post" enctype="multipart/form-data">
                <input type="text" name="text" value="<#if message.text??>${message.text}</#if>"/>
                <input type="text" name="tag" value="<#if message.tag??>${message.tag}</#if>"/>
                <input type="hidden" value="${message.id}" name="messageId"/> <#--не понятно зачем-->
                <button type="submit" name="save">Save</button>
                <div>
                               <input type="file" name="file" value="<#if message.filename??>${message.filename}</#if>"/>
                               <#if message.filename??>
                                         <img src="/img/${message.filename}"/>
                               </#if>
                </div>
                <input type="hidden" name="_csrf" value="${_csrf.token}" />
        </form>
    </div>
</@c.page>