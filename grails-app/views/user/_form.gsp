
<div class="fieldcontain ${hasErrors(bean: userInstance, field: 'keywords', 'error')} ">
  <div>
    <g:each var="category" in="${categories}">
      <div id="category_${category.id}" class="category_keywords fieldcontain">
        <label>
          <g:translate value="${category.title}"/>
        </label>
        <input type="text" class="tag" id="keywords_${category.id}" name="${category.id}_keywords[]" value=""/>
        <g:each in="${userInstance.keywords?.findAll{it.category == category}}" status="i" var="keyword">
          <input type="text" name="${category.id}_keywords[${keyword.id}]" value="${keyword.title}" class="tag"/>
        </g:each>
      </div>
    </g:each>
  </div>
</div>


