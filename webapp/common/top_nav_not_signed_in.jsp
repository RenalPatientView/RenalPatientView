<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %>

      <tr>
        <td colspan="2" bgcolor="#CFE6FC">
          <div class="navbar">
            <ul class="nav">
              <li class='<%= ("index".equals(request.getAttribute("currentNav"))) ? "navcell active" : "navcell" %>'><html:link action="/index">Home</html:link></li>
            </ul>
          </div>
        </td>
      </tr>
