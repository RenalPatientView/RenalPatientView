package com.worthsoln.patientview.edtacode;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import net.sf.hibernate.Session;
import net.sf.hibernate.Transaction;
import com.worthsoln.HibernateUtil;
import com.worthsoln.database.action.DatabaseAction;
import com.worthsoln.patientview.logon.LogonUtils;

public class EdtaCodeDeleteAction extends DatabaseAction {

    public ActionForward execute(
        ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        String edtaCodeId = request.getParameter("edtaCode");
        Session session = HibernateUtil.currentSession();
        Transaction tx = session.beginTransaction();

        session.delete(new EdtaCode(edtaCodeId));
        tx.commit();
        HibernateUtil.closeSession();

        return LogonUtils.logonChecks(mapping, request);
    }

    public String getDatabaseName() {
        return "patientview";
    }

    public String getIdentifier() {
        return "edtaCode";
    }
}
