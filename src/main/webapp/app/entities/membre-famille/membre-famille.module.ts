import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SenPaieSharedModule } from '../../shared';
import { SenPaieAdminModule } from '../../admin/admin.module';
import {
    MembreFamilleService,
    MembreFamillePopupService,
    MembreFamilleComponent,
    MembreFamilleDetailComponent,
    MembreFamilleDialogComponent,
    MembreFamillePopupComponent,
    MembreFamilleDeletePopupComponent,
    MembreFamilleDeleteDialogComponent,
    membreFamilleRoute,
    membreFamillePopupRoute,
    MembreFamilleResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...membreFamilleRoute,
    ...membreFamillePopupRoute,
];

@NgModule({
    imports: [
        SenPaieSharedModule,
        SenPaieAdminModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        MembreFamilleComponent,
        MembreFamilleDetailComponent,
        MembreFamilleDialogComponent,
        MembreFamilleDeleteDialogComponent,
        MembreFamillePopupComponent,
        MembreFamilleDeletePopupComponent,
    ],
    entryComponents: [
        MembreFamilleComponent,
        MembreFamilleDialogComponent,
        MembreFamillePopupComponent,
        MembreFamilleDeleteDialogComponent,
        MembreFamilleDeletePopupComponent,
    ],
    providers: [
        MembreFamilleService,
        MembreFamillePopupService,
        MembreFamilleResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SenPaieMembreFamilleModule {}
