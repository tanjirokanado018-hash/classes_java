package androidx.constraintlayout.solver.widgets;

import androidx.constraintlayout.solver.ArrayRow;
import androidx.constraintlayout.solver.LinearSystem;
import androidx.constraintlayout.solver.SolverVariable;
import java.util.ArrayList;

/* JADX INFO: loaded from: /home/ubuntu/upload/classes.dex */
class Chain {
    private static final boolean DEBUG = false;

    public static void applyChainConstraints(ConstraintWidgetContainer constraintWidgetContainer, LinearSystem linearSystem, int i2) {
        int i3;
        ChainHead[] chainHeadArr;
        int i4;
        if (i2 == 0) {
            i3 = constraintWidgetContainer.mHorizontalChainsSize;
            chainHeadArr = constraintWidgetContainer.mHorizontalChainsArray;
            i4 = 0;
        } else {
            i3 = constraintWidgetContainer.mVerticalChainsSize;
            chainHeadArr = constraintWidgetContainer.mVerticalChainsArray;
            i4 = 2;
        }
        for (int i5 = 0; i5 < i3; i5++) {
            ChainHead chainHead = chainHeadArr[i5];
            chainHead.define();
            applyChainConstraints(constraintWidgetContainer, linearSystem, i2, i4, chainHead);
        }
    }

    /* JADX WARN: Code duplicated, block: B:16:0x002f A[PHI: r8 r15
      0x002f: PHI (r8v43 boolean) = (r8v1 boolean), (r8v45 boolean) binds: [B:26:0x0044, B:15:0x002d] A[DONT_GENERATE, DONT_INLINE]
      0x002f: PHI (r15v31 boolean) = (r15v1 boolean), (r15v33 boolean) binds: [B:26:0x0044, B:15:0x002d] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Code duplicated, block: B:172:0x02cd  */
    /* JADX WARN: Code duplicated, block: B:17:0x0031 A[PHI: r8 r15
      0x0031: PHI (r8v3 boolean) = (r8v1 boolean), (r8v45 boolean) binds: [B:26:0x0044, B:15:0x002d] A[DONT_GENERATE, DONT_INLINE]
      0x0031: PHI (r15v3 boolean) = (r15v1 boolean), (r15v33 boolean) binds: [B:26:0x0044, B:15:0x002d] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Code duplicated, block: B:188:0x030c  */
    /* JADX WARN: Code duplicated, block: B:189:0x031e  */
    /* JADX WARN: Code duplicated, block: B:191:0x0326  */
    /* JADX WARN: Code duplicated, block: B:192:0x0329  */
    /* JADX WARN: Code duplicated, block: B:195:0x0333  */
    /* JADX WARN: Code duplicated, block: B:197:0x033a  */
    /* JADX WARN: Code duplicated, block: B:254:0x0402  */
    /* JADX WARN: Code duplicated, block: B:255:0x040a  */
    /* JADX WARN: Code duplicated, block: B:258:0x0417  */
    /* JADX WARN: Code duplicated, block: B:259:0x041a  */
    /* JADX WARN: Code duplicated, block: B:265:0x043a  */
    /* JADX WARN: Code duplicated, block: B:93:0x015b  */
    /* JADX WARN: Code duplicated, block: B:95:0x015e  */
    /* JADX WARN: Code duplicated, block: B:97:0x0164  */
    /* JADX WARN: Code duplicated, block: B:99:0x0185  */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r38v0, types: [androidx.constraintlayout.solver.LinearSystem] */
    /* JADX WARN: Type inference failed for: r5v27 */
    /* JADX WARN: Type inference failed for: r5v28 */
    /* JADX WARN: Type inference failed for: r5v29, types: [androidx.constraintlayout.solver.SolverVariable] */
    /* JADX WARN: Type inference failed for: r5v33 */
    /* JADX WARN: Type inference failed for: r5v40 */
    /* JADX WARN: Type inference failed for: r8v41 */
    /* JADX WARN: Type inference failed for: r8v42 */
    /* JADX WARN: Type inference failed for: r8v47 */
    /* JADX WARN: Type inference failed for: r8v5 */
    /* JADX WARN: Type inference failed for: r8v6, types: [androidx.constraintlayout.solver.widgets.ConstraintWidget] */
    public static void applyChainConstraints(ConstraintWidgetContainer constraintWidgetContainer, LinearSystem linearSystem, int i2, int i3, ChainHead chainHead) {
        boolean z2;
        boolean z3;
        boolean z4;
        Object obj;
        int i4;
        ConstraintAnchor constraintAnchor;
        SolverVariable solverVariable;
        ConstraintAnchor constraintAnchor2;
        SolverVariable solverVariable2;
        int margin;
        int i5;
        ConstraintAnchor constraintAnchor3;
        int margin2;
        int i6;
        int margin3;
        ConstraintAnchor constraintAnchor4;
        SolverVariable solverVariable3;
        SolverVariable solverVariable4;
        ?? r5;
        SolverVariable solverVariable5;
        int size;
        ConstraintAnchor constraintAnchor5;
        int i7;
        ConstraintWidget constraintWidget = chainHead.mFirst;
        ConstraintWidget constraintWidget2 = chainHead.mLast;
        ConstraintWidget constraintWidget3 = chainHead.mFirstVisibleWidget;
        ConstraintWidget constraintWidget4 = chainHead.mLastVisibleWidget;
        ConstraintWidget constraintWidget5 = chainHead.mHead;
        float f2 = chainHead.mTotalWeight;
        boolean z5 = constraintWidgetContainer.mListDimensionBehaviors[i2] == ConstraintWidget.DimensionBehaviour.WRAP_CONTENT;
        if (i2 == 0) {
            int i8 = constraintWidget5.mHorizontalChainStyle;
            z2 = i8 == 0;
            z3 = i8 == 1;
            if (i8 == 2) {
                z4 = true;
            } else {
                z4 = false;
            }
        } else {
            int i9 = constraintWidget5.mVerticalChainStyle;
            z2 = i9 == 0;
            z3 = i9 == 1;
            if (i9 == 2) {
                z4 = true;
            } else {
                z4 = false;
            }
        }
        boolean z6 = z3;
        boolean z7 = false;
        boolean z8 = z2;
        ?? r8 = constraintWidget;
        while (true) {
            obj = null;
            if (z7) {
                break;
            }
            ConstraintAnchor constraintAnchor6 = r8.mListAnchors[i3];
            int i10 = z4 ? 1 : 4;
            int margin4 = constraintAnchor6.getMargin();
            ConstraintWidget.DimensionBehaviour dimensionBehaviour = r8.mListDimensionBehaviors[i2];
            float f3 = f2;
            ConstraintWidget.DimensionBehaviour dimensionBehaviour2 = ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT;
            boolean z9 = dimensionBehaviour == dimensionBehaviour2 && r8.mResolvedMatchConstraintDefault[i2] == 0;
            ConstraintAnchor constraintAnchor7 = constraintAnchor6.mTarget;
            if (constraintAnchor7 != null && r8 != constraintWidget) {
                margin4 = constraintAnchor7.getMargin() + margin4;
            }
            int i11 = margin4;
            if (z4 && r8 != constraintWidget && r8 != constraintWidget3) {
                i10 = 5;
            }
            ConstraintAnchor constraintAnchor8 = constraintAnchor6.mTarget;
            ConstraintWidget constraintWidget6 = constraintWidget5;
            if (constraintAnchor8 != null) {
                if (r8 == constraintWidget3) {
                    linearSystem.addGreaterThan(constraintAnchor6.mSolverVariable, constraintAnchor8.mSolverVariable, i11, 6);
                } else {
                    linearSystem.addGreaterThan(constraintAnchor6.mSolverVariable, constraintAnchor8.mSolverVariable, i11, 8);
                }
                linearSystem.addEquality(constraintAnchor6.mSolverVariable, constraintAnchor6.mTarget.mSolverVariable, i11, (!z9 || z4) ? i10 : 5);
            } else {
                constraintWidget = constraintWidget;
            }
            if (z5) {
                if (r8.getVisibility() == 8 || r8.mListDimensionBehaviors[i2] != dimensionBehaviour2) {
                    i7 = 0;
                } else {
                    ConstraintAnchor[] constraintAnchorArr = r8.mListAnchors;
                    i7 = 0;
                    linearSystem.addGreaterThan(constraintAnchorArr[i3 + 1].mSolverVariable, constraintAnchorArr[i3].mSolverVariable, 0, 5);
                }
                linearSystem.addGreaterThan(r8.mListAnchors[i3].mSolverVariable, constraintWidgetContainer.mListAnchors[i3].mSolverVariable, i7, 8);
            }
            ConstraintAnchor constraintAnchor9 = r8.mListAnchors[i3 + 1].mTarget;
            if (constraintAnchor9 != null) {
                ConstraintWidget constraintWidget7 = constraintAnchor9.mOwner;
                ConstraintAnchor constraintAnchor10 = constraintWidget7.mListAnchors[i3].mTarget;
                if (constraintAnchor10 != null && constraintAnchor10.mOwner == r8) {
                    obj = constraintWidget7;
                }
            }
            if (obj != null) {
                r8 = obj;
                z7 = z7;
            } else {
                z7 = true;
            }
            z8 = z8;
            f2 = f3;
            constraintWidget5 = constraintWidget6;
            constraintWidget = constraintWidget;
            r8 = r8;
        }
        ConstraintWidget constraintWidget8 = constraintWidget5;
        float f4 = f2;
        ConstraintWidget constraintWidget9 = constraintWidget;
        boolean z10 = z8;
        if (constraintWidget4 != null) {
            int i12 = i3 + 1;
            if (constraintWidget2.mListAnchors[i12].mTarget != null) {
                ConstraintAnchor constraintAnchor11 = constraintWidget4.mListAnchors[i12];
                if (constraintWidget4.mListDimensionBehaviors[i2] == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT && constraintWidget4.mResolvedMatchConstraintDefault[i2] == 0 && !z4) {
                    ConstraintAnchor constraintAnchor12 = constraintAnchor11.mTarget;
                    if (constraintAnchor12.mOwner == constraintWidgetContainer) {
                        linearSystem.addEquality(constraintAnchor11.mSolverVariable, constraintAnchor12.mSolverVariable, -constraintAnchor11.getMargin(), 5);
                    } else if (z4) {
                        constraintAnchor5 = constraintAnchor11.mTarget;
                        if (constraintAnchor5.mOwner == constraintWidgetContainer) {
                            linearSystem.addEquality(constraintAnchor11.mSolverVariable, constraintAnchor5.mSolverVariable, -constraintAnchor11.getMargin(), 4);
                        }
                    }
                } else if (z4) {
                    constraintAnchor5 = constraintAnchor11.mTarget;
                    if (constraintAnchor5.mOwner == constraintWidgetContainer) {
                        linearSystem.addEquality(constraintAnchor11.mSolverVariable, constraintAnchor5.mSolverVariable, -constraintAnchor11.getMargin(), 4);
                    }
                }
                linearSystem.addLowerThan(constraintAnchor11.mSolverVariable, constraintWidget2.mListAnchors[i12].mTarget.mSolverVariable, -constraintAnchor11.getMargin(), 6);
            }
        }
        if (z5) {
            int i13 = i3 + 1;
            SolverVariable solverVariable6 = constraintWidgetContainer.mListAnchors[i13].mSolverVariable;
            ConstraintAnchor constraintAnchor13 = constraintWidget2.mListAnchors[i13];
            linearSystem.addGreaterThan(solverVariable6, constraintAnchor13.mSolverVariable, constraintAnchor13.getMargin(), 8);
        }
        ArrayList<ConstraintWidget> arrayList = chainHead.mWeightedMatchConstraintsWidgets;
        if (arrayList != null && (size = arrayList.size()) > 1) {
            float f5 = (!chainHead.mHasUndefinedWeights || chainHead.mHasComplexMatchWeights) ? f4 : chainHead.mWidgetsMatchCount;
            float f6 = 0.0f;
            ConstraintWidget constraintWidget10 = null;
            int i14 = 0;
            float f7 = 0.0f;
            while (i14 < size) {
                ConstraintWidget constraintWidget11 = arrayList.get(i14);
                float f8 = constraintWidget11.mWeight[i2];
                if (f8 < f6) {
                    if (chainHead.mHasComplexMatchWeights) {
                        ConstraintAnchor[] constraintAnchorArr2 = constraintWidget11.mListAnchors;
                        linearSystem.addEquality(constraintAnchorArr2[i3 + 1].mSolverVariable, constraintAnchorArr2[i3].mSolverVariable, 0, 4);
                    } else {
                        f8 = 1.0f;
                    }
                    arrayList = arrayList;
                    i14++;
                    arrayList = arrayList;
                    f6 = 0.0f;
                }
                if (f8 == f6) {
                    ConstraintAnchor[] constraintAnchorArr3 = constraintWidget11.mListAnchors;
                    linearSystem.addEquality(constraintAnchorArr3[i3 + 1].mSolverVariable, constraintAnchorArr3[i3].mSolverVariable, 0, 8);
                    arrayList = arrayList;
                } else {
                    if (constraintWidget10 != null) {
                        ConstraintAnchor[] constraintAnchorArr4 = constraintWidget10.mListAnchors;
                        SolverVariable solverVariable7 = constraintAnchorArr4[i3].mSolverVariable;
                        int i15 = i3 + 1;
                        SolverVariable solverVariable8 = constraintAnchorArr4[i15].mSolverVariable;
                        ConstraintAnchor[] constraintAnchorArr5 = constraintWidget11.mListAnchors;
                        SolverVariable solverVariable9 = constraintAnchorArr5[i3].mSolverVariable;
                        SolverVariable solverVariable10 = constraintAnchorArr5[i15].mSolverVariable;
                        ArrayRow arrayRowCreateRow = linearSystem.createRow();
                        arrayRowCreateRow.createRowEqualMatchDimensions(f7, f5, f8, solverVariable7, solverVariable8, solverVariable9, solverVariable10);
                        linearSystem.addConstraint(arrayRowCreateRow);
                    }
                    constraintWidget10 = constraintWidget11;
                    f7 = f8;
                }
                i14++;
                arrayList = arrayList;
                f6 = 0.0f;
            }
        }
        if (constraintWidget3 != null && (constraintWidget3 == constraintWidget4 || z4)) {
            ConstraintAnchor constraintAnchor14 = constraintWidget9.mListAnchors[i3];
            int i16 = i3 + 1;
            ConstraintAnchor constraintAnchor15 = constraintWidget2.mListAnchors[i16];
            ConstraintAnchor constraintAnchor16 = constraintAnchor14.mTarget;
            SolverVariable solverVariable11 = constraintAnchor16 != null ? constraintAnchor16.mSolverVariable : null;
            ConstraintAnchor constraintAnchor17 = constraintAnchor15.mTarget;
            SolverVariable solverVariable12 = constraintAnchor17 != null ? constraintAnchor17.mSolverVariable : null;
            ConstraintAnchor constraintAnchor18 = constraintWidget3.mListAnchors[i3];
            ConstraintAnchor constraintAnchor19 = constraintWidget4.mListAnchors[i16];
            if (solverVariable11 != null && solverVariable12 != null) {
                linearSystem.addCentering(constraintAnchor18.mSolverVariable, solverVariable11, constraintAnchor18.getMargin(), i2 == 0 ? constraintWidget8.mHorizontalBiasPercent : constraintWidget8.mVerticalBiasPercent, solverVariable12, constraintAnchor19.mSolverVariable, constraintAnchor19.getMargin(), 7);
            }
        } else if (!z10 || constraintWidget3 == null) {
            int i17 = 8;
            if (z6 && constraintWidget3 != null) {
                int i18 = chainHead.mWidgetsMatchCount;
                boolean z11 = i18 > 0 && chainHead.mWidgetsCount == i18;
                ConstraintWidget constraintWidget12 = constraintWidget3;
                ConstraintWidget constraintWidget13 = constraintWidget12;
                while (constraintWidget12 != null) {
                    ConstraintWidget constraintWidget14 = constraintWidget12.mNextChainWidget[i2];
                    while (constraintWidget14 != null && constraintWidget14.getVisibility() == i17) {
                        constraintWidget14 = constraintWidget14.mNextChainWidget[i2];
                    }
                    if (constraintWidget12 == constraintWidget3 || constraintWidget12 == constraintWidget4 || constraintWidget14 == null) {
                        constraintWidget13 = constraintWidget13;
                        i4 = 8;
                    } else {
                        ConstraintWidget constraintWidget15 = constraintWidget14 == constraintWidget4 ? null : constraintWidget14;
                        ConstraintAnchor constraintAnchor20 = constraintWidget12.mListAnchors[i3];
                        SolverVariable solverVariable13 = constraintAnchor20.mSolverVariable;
                        ConstraintAnchor constraintAnchor21 = constraintAnchor20.mTarget;
                        if (constraintAnchor21 != null) {
                            SolverVariable solverVariable14 = constraintAnchor21.mSolverVariable;
                        }
                        int i19 = i3 + 1;
                        SolverVariable solverVariable15 = constraintWidget13.mListAnchors[i19].mSolverVariable;
                        int margin5 = constraintAnchor20.getMargin();
                        int margin6 = constraintWidget12.mListAnchors[i19].getMargin();
                        if (constraintWidget15 != null) {
                            constraintAnchor = constraintWidget15.mListAnchors[i3];
                            solverVariable = constraintAnchor.mSolverVariable;
                            constraintAnchor2 = constraintAnchor.mTarget;
                            if (constraintAnchor2 == null) {
                                solverVariable2 = null;
                            }
                            if (constraintAnchor != null) {
                                margin = constraintAnchor.getMargin() + margin6;
                            } else {
                                margin = margin6;
                            }
                            int margin7 = constraintWidget13.mListAnchors[i19].getMargin() + margin5;
                            if (z11) {
                                i5 = 8;
                            } else {
                                i5 = 4;
                            }
                            if (solverVariable13 != null || solverVariable15 == null || solverVariable == null || solverVariable2 == null) {
                                i4 = 8;
                            } else {
                                i4 = 8;
                                linearSystem.addCentering(solverVariable13, solverVariable15, margin7, 0.5f, solverVariable, solverVariable2, margin, i5);
                            }
                            constraintWidget14 = constraintWidget15;
                        } else {
                            constraintAnchor = constraintWidget4.mListAnchors[i3];
                            solverVariable = constraintAnchor != null ? constraintAnchor.mSolverVariable : null;
                            constraintAnchor2 = constraintWidget12.mListAnchors[i19];
                        }
                        solverVariable2 = constraintAnchor2.mSolverVariable;
                        if (constraintAnchor != null) {
                            margin = constraintAnchor.getMargin() + margin6;
                        } else {
                            margin = margin6;
                        }
                        int margin8 = constraintWidget13.mListAnchors[i19].getMargin() + margin5;
                        if (z11) {
                            i5 = 8;
                        } else {
                            i5 = 4;
                        }
                        if (solverVariable13 != null) {
                            i4 = 8;
                        } else {
                            i4 = 8;
                        }
                        constraintWidget14 = constraintWidget15;
                    }
                    if (constraintWidget12.getVisibility() == i4) {
                        constraintWidget12 = constraintWidget13;
                    }
                    constraintWidget13 = constraintWidget12;
                    i17 = 8;
                    constraintWidget12 = constraintWidget14;
                }
                ConstraintAnchor constraintAnchor22 = constraintWidget3.mListAnchors[i3];
                ConstraintAnchor constraintAnchor23 = constraintWidget9.mListAnchors[i3].mTarget;
                int i20 = i3 + 1;
                ConstraintAnchor constraintAnchor24 = constraintWidget4.mListAnchors[i20];
                ConstraintAnchor constraintAnchor25 = constraintWidget2.mListAnchors[i20].mTarget;
                if (constraintAnchor23 != null) {
                    if (constraintWidget3 != constraintWidget4) {
                        linearSystem.addEquality(constraintAnchor22.mSolverVariable, constraintAnchor23.mSolverVariable, constraintAnchor22.getMargin(), 5);
                    } else if (constraintAnchor25 != null) {
                        linearSystem.addCentering(constraintAnchor22.mSolverVariable, constraintAnchor23.mSolverVariable, constraintAnchor22.getMargin(), 0.5f, constraintAnchor24.mSolverVariable, constraintAnchor25.mSolverVariable, constraintAnchor24.getMargin(), 5);
                    }
                }
                if (constraintAnchor25 != null && constraintWidget3 != constraintWidget4) {
                    linearSystem.addEquality(constraintAnchor24.mSolverVariable, constraintAnchor25.mSolverVariable, -constraintAnchor24.getMargin(), 5);
                }
            }
        } else {
            int i21 = chainHead.mWidgetsMatchCount;
            boolean z12 = i21 > 0 && chainHead.mWidgetsCount == i21;
            ConstraintWidget constraintWidget16 = constraintWidget3;
            ConstraintWidget constraintWidget17 = constraintWidget16;
            while (constraintWidget16 != null) {
                ConstraintWidget constraintWidget18 = constraintWidget16.mNextChainWidget[i2];
                while (constraintWidget18 != null && constraintWidget18.getVisibility() == 8) {
                    constraintWidget18 = constraintWidget18.mNextChainWidget[i2];
                }
                if (constraintWidget18 != null || constraintWidget16 == constraintWidget4) {
                    ConstraintAnchor constraintAnchor26 = constraintWidget16.mListAnchors[i3];
                    SolverVariable solverVariable16 = constraintAnchor26.mSolverVariable;
                    ConstraintAnchor constraintAnchor27 = constraintAnchor26.mTarget;
                    SolverVariable solverVariable17 = constraintAnchor27 != null ? constraintAnchor27.mSolverVariable : null;
                    if (constraintWidget17 != constraintWidget16) {
                        constraintAnchor3 = constraintWidget17.mListAnchors[i3 + 1];
                    } else {
                        if (constraintWidget16 == constraintWidget3 && constraintWidget17 == constraintWidget16) {
                            constraintAnchor3 = constraintWidget9.mListAnchors[i3].mTarget;
                            if (constraintAnchor3 == null) {
                                solverVariable17 = null;
                            }
                        }
                        margin2 = constraintAnchor26.getMargin();
                        i6 = i3 + 1;
                        margin3 = constraintWidget16.mListAnchors[i6].getMargin();
                        if (constraintWidget18 != null) {
                            constraintAnchor4 = constraintWidget18.mListAnchors[i3];
                            SolverVariable solverVariable18 = constraintAnchor4.mSolverVariable;
                            solverVariable4 = constraintWidget16.mListAnchors[i6].mSolverVariable;
                            solverVariable3 = solverVariable18;
                        } else {
                            constraintAnchor4 = constraintWidget2.mListAnchors[i6].mTarget;
                            if (constraintAnchor4 != null) {
                                solverVariable3 = constraintAnchor4.mSolverVariable;
                            } else {
                                solverVariable3 = null;
                            }
                            solverVariable4 = constraintWidget16.mListAnchors[i6].mSolverVariable;
                        }
                        if (constraintAnchor4 != null) {
                            margin3 += constraintAnchor4.getMargin();
                        }
                        if (constraintWidget17 != null) {
                            margin2 += constraintWidget17.mListAnchors[i6].getMargin();
                        }
                        if (solverVariable16 == null && solverVariable17 != null && solverVariable3 != null && solverVariable4 != null) {
                            if (constraintWidget16 == constraintWidget3) {
                                margin2 = constraintWidget3.mListAnchors[i3].getMargin();
                            }
                            linearSystem.addCentering(solverVariable16, solverVariable17, margin2, 0.5f, solverVariable3, solverVariable4, constraintWidget16 == constraintWidget4 ? constraintWidget4.mListAnchors[i6].getMargin() : margin3, z12 ? 8 : 5);
                        }
                    }
                    solverVariable17 = constraintAnchor3.mSolverVariable;
                    margin2 = constraintAnchor26.getMargin();
                    i6 = i3 + 1;
                    margin3 = constraintWidget16.mListAnchors[i6].getMargin();
                    if (constraintWidget18 != null) {
                        constraintAnchor4 = constraintWidget18.mListAnchors[i3];
                        SolverVariable solverVariable19 = constraintAnchor4.mSolverVariable;
                        solverVariable4 = constraintWidget16.mListAnchors[i6].mSolverVariable;
                        solverVariable3 = solverVariable19;
                    } else {
                        constraintAnchor4 = constraintWidget2.mListAnchors[i6].mTarget;
                        if (constraintAnchor4 != null) {
                            solverVariable3 = constraintAnchor4.mSolverVariable;
                        } else {
                            solverVariable3 = null;
                        }
                        solverVariable4 = constraintWidget16.mListAnchors[i6].mSolverVariable;
                    }
                    if (constraintAnchor4 != null) {
                        margin3 += constraintAnchor4.getMargin();
                    }
                    if (constraintWidget17 != null) {
                        margin2 += constraintWidget17.mListAnchors[i6].getMargin();
                    }
                    if (solverVariable16 == null) {
                    }
                }
                if (constraintWidget16.getVisibility() != 8) {
                    constraintWidget17 = constraintWidget16;
                }
                constraintWidget16 = constraintWidget18;
            }
        }
        if ((!z10 && !z6) || constraintWidget3 == null || constraintWidget3 == constraintWidget4) {
            return;
        }
        ConstraintAnchor[] constraintAnchorArr6 = constraintWidget3.mListAnchors;
        ConstraintAnchor constraintAnchor28 = constraintAnchorArr6[i3];
        int i22 = i3 + 1;
        ConstraintAnchor constraintAnchor29 = constraintWidget4.mListAnchors[i22];
        ConstraintAnchor constraintAnchor30 = constraintAnchor28.mTarget;
        SolverVariable solverVariable20 = constraintAnchor30 != null ? constraintAnchor30.mSolverVariable : null;
        ConstraintAnchor constraintAnchor31 = constraintAnchor29.mTarget;
        if (constraintAnchor31 != null) {
            solverVariable5 = constraintAnchor31.mSolverVariable;
        } else {
            r5 = 0;
        }
        if (constraintWidget2 != constraintWidget4) {
            ConstraintAnchor constraintAnchor32 = constraintWidget2.mListAnchors[i22].mTarget;
            if (constraintAnchor32 != null) {
                r5 = solverVariable5;
                obj = constraintAnchor32.mSolverVariable;
            }
            r5 = solverVariable5;
            r5 = obj;
        }
        if (constraintWidget3 == constraintWidget4) {
            constraintAnchor29 = constraintAnchorArr6[i22];
        }
        if (solverVariable20 == null || r5 == 0) {
            return;
        }
        linearSystem.addCentering(constraintAnchor28.mSolverVariable, solverVariable20, constraintAnchor28.getMargin(), 0.5f, r5, constraintAnchor29.mSolverVariable, constraintWidget4.mListAnchors[i22].getMargin(), 5);
    }
}
