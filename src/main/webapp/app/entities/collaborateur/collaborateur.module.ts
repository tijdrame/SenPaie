import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SenPaieSharedModule } from '../../shared';
import { SenPaieAdminModule } from '../../admin/admin.module';
import {
    CollaborateurService,
    CollaborateurPopupService,
    CollaborateurComponent,
    CollaborateurDetailComponent,
    CollaborateurDialogComponent,
    CollaborateurPopupComponent,
    CollaborateurDeletePopupComponent,
    CollaborateurDeleteDialogComponent,
    collaborateurRoute,
    collaborateurPopupRoute,
    CollaborateurResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...collaborateurRoute,
    ...collaborateurPopupRoute,
];

@NgModule({
    imports: [
        SenPaieSharedModule,
        SenPaieAdminModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        CollaborateurComponent,
        CollaborateurDetailComponent,
        CollaborateurDialogComponent,
        CollaborateurDeleteDialogComponent,
        CollaborateurPopupComponent,
        CollaborateurDeletePopupComponent,
    ],
    entryComponents: [
        CollaborateurComponent,
        CollaborateurDialogComponent,
        CollaborateurPopupComponent,
        CollaborateurDeleteDialogComponent,
        CollaborateurDeletePopupComponent,
    ],
    providers: [
        CollaborateurService,
        CollaborateurPopupService,
        CollaborateurResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SenPaieCollaborateurModule {}
