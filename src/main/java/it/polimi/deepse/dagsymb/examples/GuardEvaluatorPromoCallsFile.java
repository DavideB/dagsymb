package it.polimi.deepse.dagsymb.examples;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class GuardEvaluatorPromoCallsFile implements IGuardEvaluator {

    private List<Integer> satisfiableGuards;

    @Override
    public List<Integer> evaluateGuards(Map<String, Object> knownValues) {
        satisfiableGuards = new ArrayList<>();

        extractValues(knownValues);

        evaluateActualGuards();

        return satisfiableGuards;
    }	

    private void evaluateActualGuards() {
        //path condition evaluation
        if (
           ( !arg0_known || 	arg0 > 100 ) &&
           ( !arg3_known || 	arg3 >= 0 ) &&
           ( !arg3_known || 	arg3 <= 2 ) &&
           ( !count_PromoCallsFile_java_29_0_known || !arg1_known || 	count_PromoCallsFile_java_29_0 - arg1 <= 0 ) &&
           ( !arg2_known || !count_PromoCallsFile_java_33_0_known || 	count_PromoCallsFile_java_33_0 - arg2 > 0 ) &&
        true) {
            satisfiableGuards.add(0);
        }

        if (
           ( !arg0_known || 	arg0 > 100 ) &&
           ( !arg3_known || 	arg3 >= 0 ) &&
           ( !arg3_known || 	arg3 <= 2 ) &&
           ( !count_PromoCallsFile_java_29_0_known || !arg1_known || 	count_PromoCallsFile_java_29_0 - arg1 <= 0 ) &&
           ( !arg2_known || !count_PromoCallsFile_java_33_0_known || 	count_PromoCallsFile_java_33_0 - arg2 <= 0) &&
        true) {
            satisfiableGuards.add(1);
        }

        if (
           ( !arg0_known || 	arg0 > 100 ) &&
           ( !arg3_known || 	arg3 >= 0 ) &&
           ( !arg3_known || 	arg3 <= 2 ) &&
           ( !count_PromoCallsFile_java_29_0_known || !arg1_known || 	count_PromoCallsFile_java_29_0 - arg1 > 0 ) &&
           ( !arg3_known || 	1 > arg3 ) &&
           ( !arg2_known || !count_PromoCallsFile_java_33_0_known || 	count_PromoCallsFile_java_33_0 - arg2 > 0 ) &&
        true) {
            satisfiableGuards.add(2);
        }

        if (
           ( !arg0_known || 	arg0 > 100 ) &&
           ( !arg3_known || 	arg3 >= 0 ) &&
           ( !arg3_known || 	arg3 <= 2 ) &&
           ( !count_PromoCallsFile_java_29_0_known || !arg1_known || 	count_PromoCallsFile_java_29_0 - arg1 > 0 ) &&
           ( !arg3_known || 	1 > arg3 ) &&
           ( !arg2_known || !count_PromoCallsFile_java_33_0_known || 	count_PromoCallsFile_java_33_0 - arg2 <= 0) &&
        true) {
            satisfiableGuards.add(3);
        }

        if (
           ( !arg0_known || 	arg0 > 100 ) &&
           ( !arg3_known || 	arg3 >= 0 ) &&
           ( !arg3_known || 	arg3 <= 2 ) &&
           ( !count_PromoCallsFile_java_29_0_known || !arg1_known || 	count_PromoCallsFile_java_29_0 - arg1 > 0 ) &&
           ( !arg3_known || 	1 <= arg3 ) &&
           ( !arg3_known || 	2 > arg3 ) &&
           ( !arg2_known || !count_PromoCallsFile_java_33_0_known || 	count_PromoCallsFile_java_33_0 - arg2 > 0 ) &&
        true) {
            satisfiableGuards.add(4);
        }

        if (
           ( !arg0_known || 	arg0 > 100 ) &&
           ( !arg3_known || 	arg3 >= 0 ) &&
           ( !arg3_known || 	arg3 <= 2 ) &&
           ( !count_PromoCallsFile_java_29_0_known || !arg1_known || 	count_PromoCallsFile_java_29_0 - arg1 > 0 ) &&
           ( !arg3_known || 	1 <= arg3 ) &&
           ( !arg3_known || 	2 > arg3 ) &&
           ( !arg2_known || !count_PromoCallsFile_java_33_0_known || 	count_PromoCallsFile_java_33_0 - arg2 <= 0) &&
        true) {
            satisfiableGuards.add(5);
        }

        if (
           ( !arg0_known || 	arg0 > 100 ) &&
           ( !arg3_known || 	arg3 >= 0 ) &&
           ( !arg3_known || 	arg3 <= 2 ) &&
           ( !count_PromoCallsFile_java_29_0_known || !arg1_known || 	count_PromoCallsFile_java_29_0 - arg1 > 0 ) &&
           ( !arg3_known || 	1 <= arg3 ) &&
           ( !arg3_known || 	2 <= arg3 ) &&
           ( !arg2_known || !count_PromoCallsFile_java_33_0_known || 	count_PromoCallsFile_java_33_0 - arg2 > 0 ) &&
        true) {
            satisfiableGuards.add(6);
        }

        if (
           ( !arg0_known || 	arg0 > 100 ) &&
           ( !arg3_known || 	arg3 >= 0 ) &&
           ( !arg3_known || 	arg3 <= 2 ) &&
           ( !count_PromoCallsFile_java_29_0_known || !arg1_known || 	count_PromoCallsFile_java_29_0 - arg1 > 0 ) &&
           ( !arg3_known || 	1 <= arg3 ) &&
           ( !arg3_known || 	2 <= arg3 ) &&
           ( !arg2_known || !count_PromoCallsFile_java_33_0_known || 	count_PromoCallsFile_java_33_0 - arg2 <= 0) &&
        true) {
            satisfiableGuards.add(7);
        }

    }

    private boolean arg0_known; private Integer arg0;
    private boolean count_PromoCallsFile_java_29_0_known; private Long count_PromoCallsFile_java_29_0;
    private boolean arg2_known; private Long arg2;
    private boolean arg1_known; private Long arg1;
    private boolean count_PromoCallsFile_java_33_0_known; private Long count_PromoCallsFile_java_33_0;
    private boolean arg3_known; private Integer arg3;

    private void extractValues(Map<String, Object> knownValues) {
        arg0_known = (knownValues.get("arg0") != null);
        arg0 = arg0_known ? Integer.parseInt((String) knownValues.get("arg0")) : null;

        count_PromoCallsFile_java_29_0_known = (knownValues.get("count_PromoCallsFile.java:29_0") != null);
        count_PromoCallsFile_java_29_0 = count_PromoCallsFile_java_29_0_known ? (Long) knownValues.get("count_PromoCallsFile.java:29_0") : null;

        arg2_known = (knownValues.get("arg2") != null);
        arg2 = arg2_known ? Long.parseLong((String) knownValues.get("arg2")) : null;

        arg1_known = (knownValues.get("arg1") != null);
        arg1 = arg1_known ? Long.parseLong((String) knownValues.get("arg1")) : null;

        count_PromoCallsFile_java_33_0_known = (knownValues.get("count_PromoCallsFile.java:33_0") != null);
        count_PromoCallsFile_java_33_0 = count_PromoCallsFile_java_33_0_known ? (Long) knownValues.get("count_PromoCallsFile.java:33_0") : null;

        arg3_known = (knownValues.get("arg3") != null);
        arg3 = arg3_known ? Integer.parseInt((String) knownValues.get("arg3")) : null;

    }
}
