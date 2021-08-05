<#assign
    known=Session.SPRING_SECURITY_CONTEXT?? <#--Пользователь в системе?-->
>
<#if known>
    <#assign
        user=Session.SPRING_SECURITY_CONTEXT.authentication.principal <#--Пользователь ссесии-->
        name=user.getUsername()
        isAdmin=user.isAdmin()
        currentUserId=user.getId() <#--Id текущего пользователя-->
    >
    <#else>
        <#assign
            name="unknown"
            isAdmin=false
        currentUserId=-1
        >
</#if>

