<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %>
<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="/tags/datetime" prefix="dt" %>
<tr>
  <td colspan="10">
    <table width="100%">
      <tr>
          <logic:present role="patient,demo,superadmin,unitadmin,unitstaff">
              <td class="infostrip" align="left">logged in as: <b><%= request.getUserPrincipal().getName()%>
              </b> <html:link action="logout">log out</html:link></td>
          </logic:present>

        <logic:notPresent role="patient,demo,superadmin,unitadmin,unitstaff">
          <td class="infostrip" align="left">&nbsp;&nbsp;<html:link action="/logged_in">log in</html:link></td>
        </logic:notPresent>

        <logic:present role="patient">
          <logic:notPresent user="testhd">
            <logic:notPresent user="testpd">
              <logic:notPresent user="testgen">
                <logic:notPresent user="testtx">
                  <td class="infostrip" align="left"><html:link
                      forward="patientPasswordChangeInput">change password</html:link></td>
                </logic:notPresent>
              </logic:notPresent>
            </logic:notPresent>
          </logic:notPresent>
        </logic:present>

        <logic:present role="superadmin,unitadmin,unitstaff">
          <td class="infostrip" align="left"><html:link
              forward="controlPasswordChangeInput">change password</html:link></td>
        </logic:present>

        <logic:present role="superadmin,unitadmin,unitstaff">
          <td class="infostrip"><html:link forward="control">back to Admin Area</html:link></td>
        </logic:present>

        <td class="infostrip" align="right"><dt:format pattern="d MMM yyyy"><dt:currentTime/></dt:format></td>
      </tr>
    </table>
  </td>
</tr>
