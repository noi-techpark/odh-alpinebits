// SPDX-FileCopyrightText: NOI Techpark <digital@noi.bz.it>
//
// SPDX-License-Identifier: AGPL-3.0-or-later

/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package it.bz.opendatahub.alpinebits.xml.middleware.utils;

import org.w3c.dom.Node;
import org.xmlunit.diff.Comparison;
import org.xmlunit.diff.ComparisonResult;
import org.xmlunit.diff.DifferenceEvaluator;
import org.xmlunit.diff.DifferenceEvaluators;

/**
 * This is a custom {@link DifferenceEvaluator} for XMLUnit
 * to evaluate the difference between two XML.
 * <p>
 * The reason for this custom implementation to exist is,
 * that XMLUnit reports an error for boolean values, if
 * they differ in their spelling. For example,
 * "0" and "false" are reported as differences, although
 * they represent the same value (both spellings are
 * correct according to the XML standard). The same goes
 * for "1" and "true".
 * <p>
 * Note, that this evaluator is used for unit tests only.
 *
 * @see <a href="https://github.com/xmlunit/xmlunit">XMLUnit on GitHub</a>
 */
public class UnitTestDifferenceEvaluator implements DifferenceEvaluator {

    @Override
    public ComparisonResult evaluate(Comparison comparison, ComparisonResult outcome) {
        // If there is no difference, return early
        if (outcome == ComparisonResult.EQUAL) {
            return outcome;
        }

        // Try to evaluate differences using the default DifferenceEvaluator
        // and return ComparisonResult.SIMILAR if the default
        // DifferenceEvaluator says so
        ComparisonResult defaulEvalutorOutcome = DifferenceEvaluators.Default.evaluate(comparison, outcome);
        if (defaulEvalutorOutcome == ComparisonResult.SIMILAR) {
            return ComparisonResult.SIMILAR;
        }

        final Node controlNode = comparison.getControlDetails().getTarget();
        final Node testNode = comparison.getTestDetails().getTarget();

        // Check if nodes are boolean and match, although
        // have a different representation (e.g "0" and "false")
        if (nodesAreBooleanAndMatch(controlNode, testNode)) {
            return ComparisonResult.SIMILAR;
        }

        return outcome;
    }

    private boolean nodesAreBooleanAndMatch(Node controlNode, Node testNode) {
        return "false".equals(controlNode.getNodeValue()) && "0".equals(testNode.getNodeValue())
                || "true".equals(controlNode.getNodeValue()) && "1".equals(testNode.getNodeValue())
                || "0".equals(controlNode.getNodeValue()) && "false".equals(testNode.getNodeValue())
                || "1".equals(controlNode.getNodeValue()) && "true".equals(testNode.getNodeValue());
    }

}
