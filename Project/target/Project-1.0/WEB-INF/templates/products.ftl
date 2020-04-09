<#if check??>
    ${check}
</#if>
<#list products as product>
    <tr>
        <td>${product.id}.</td>
        <td>Name: ${product.name}.</td>
        <td>Price: ${product.price} RUB</td>
    </tr>
</#list>