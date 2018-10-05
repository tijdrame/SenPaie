import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SenPaieSharedModule } from '../../shared';
import {
    ExerciceService,
    ExercicePopupService,
    ExerciceComponent,
    ExerciceDetailComponent,
    ExerciceDialogComponent,
    ExercicePopupComponent,
    ExerciceDeletePopupComponent,
    ExerciceDeleteDialogComponent,
    exerciceRoute,
    exercicePopupRoute,
    ExerciceResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...exerciceRoute,
    ...exercicePopupRoute,
];

@NgModule({
    imports: [
        SenPaieSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        ExerciceComponent,
        ExerciceDetailComponent,
        ExerciceDialogComponent,
        ExerciceDeleteDialogComponent,
        ExercicePopupComponent,
        ExerciceDeletePopupComponent,
    ],
    entryComponents: [
        ExerciceComponent,
        ExerciceDialogComponent,
        ExercicePopupComponent,
        ExerciceDeleteDialogComponent,
        ExerciceDeletePopupComponent,
    ],
    providers: [
        ExerciceService,
        ExercicePopupService,
        ExerciceResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SenPaieExerciceModule {}
