<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %>

  <tr>
        <td colspan="2" bgcolor="#CFE6FC">
          <div class="navbar">
            <ul class="nav">
              <li class='<%= ("index".equals(request.getAttribute("currentNav"))) ? "navcell active" : "navcell" %>'><html:link action="/index">Home</html:link></li>
              <li class='<%= ("patient_details".equals(request.getAttribute("currentNav"))) ? "navcell active" : "navcell" %>'><html:link action="/patient/patient_details">Patient Details</html:link></li>
              <li class='<%= ("patient_view".equals(request.getAttribute("currentNav"))) ? "navcell active" : "navcell" %>'><html:link action="/patient/patient_view">Patient Info</html:link></li>
              <li class='<%= ("aboutme".equals(request.getAttribute("currentNav"))) ? "navcell active" : "navcell" %>'><html:link action="/patient/aboutme">About Me</html:link></li>
              <li class='<%= ("patient_entry".equals(request.getAttribute("currentNav"))) ? "navcell active" : "navcell" %>'><html:link action="/patient/patient_entry">Enter My...</html:link></li>
              <li class='<%= ("results".equals(request.getAttribute("currentNav"))) ? "navcell active" : "navcell" %>'><html:link action="/patient/results">Results</html:link></li>
              <li class='<%= ("medicines".equals(request.getAttribute("currentNav"))) ? "navcell active" : "navcell" %>'><html:link action="/patient/medicines">Medicines</html:link></li>
              <li class='<%= ("letters".equals(request.getAttribute("currentNav"))) ? "navcell active" : "navcell" %>'><html:link action="/patient/letters">Letters</html:link></li>
              <li class='<%= ("contact".equals(request.getAttribute("currentNav"))) ? "navcell active" : "navcell" %>'><html:link action="/patient/contact">Contact</html:link></li>
              <li class='<%= ("xxxxxxx".equals(request.getAttribute("currentNav"))) ? "navcell active" : "navcell" %>'><html:link href="/forums/list.page">Forum</html:link></li>
              <li class='<%= ("help".equals(request.getAttribute("currentNav"))) ? "navcell active" : "navcell" %>'><html:link action="/help">Help</html:link></li>
            </ul>
          </div>
        </td>
      </tr>
