import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SenPaieSharedModule } from '../../shared';
import {
    TypeAbsenceService,
    TypeAbsencePopupService,
    TypeAbsenceComponent,
    TypeAbsenceDetailComponent,
    TypeAbsenceDialogComponent,
    TypeAbsencePopupComponent,
    TypeAbsenceDeletePopupComponent,
    TypeAbsenceDeleteDialogComponent,
    typeAbsenceRoute,
    typeAbsencePopupRoute,
    TypeAbsenceResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...typeAbsenceRoute,
    ...typeAbsencePopupRoute,
];

@NgModule({
    imports: [
        SenPaieSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        TypeAbsenceComponent,
        TypeAbsenceDetailComponent,
        TypeAbsenceDialogComponent,
        TypeAbsenceDeleteDialogComponent,
        TypeAbsencePopupComponent,
        TypeAbsenceDeletePopupComponent,
    ],
    entryComponents: [
        TypeAbsenceComponent,
        TypeAbsenceDialogComponent,
        TypeAbsencePopupComponent,
        TypeAbsenceDeleteDialogComponent,
        TypeAbsenceDeletePopupComponent,
    ],
    providers: [
        TypeAbsenceService,
        TypeAbsencePopupService,
        TypeAbsenceResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SenPaieTypeAbsenceModule {}
