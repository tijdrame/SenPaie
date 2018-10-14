import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SenPaieSharedModule } from '../../shared';
import { SenPaieAdminModule } from '../../admin/admin.module';
import {
    AbsenceService,
    AbsencePopupService,
    AbsenceComponent,
    AbsenceDetailComponent,
    AbsenceDialogComponent,
    AbsencePopupComponent,
    AbsenceDeletePopupComponent,
    AbsenceDeleteDialogComponent,
    absenceRoute,
    absencePopupRoute,
    AbsenceResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...absenceRoute,
    ...absencePopupRoute,
];

@NgModule({
    imports: [
        SenPaieSharedModule,
        SenPaieAdminModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        AbsenceComponent,
        AbsenceDetailComponent,
        AbsenceDialogComponent,
        AbsenceDeleteDialogComponent,
        AbsencePopupComponent,
        AbsenceDeletePopupComponent,
    ],
    entryComponents: [
        AbsenceComponent,
        AbsenceDialogComponent,
        AbsencePopupComponent,
        AbsenceDeleteDialogComponent,
        AbsenceDeletePopupComponent,
    ],
    providers: [
        AbsenceService,
        AbsencePopupService,
        AbsenceResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SenPaieAbsenceModule {}
