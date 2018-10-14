import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SenPaieSharedModule } from '../../shared';
import { SenPaieAdminModule } from '../../admin/admin.module';
import {
    RemboursementService,
    RemboursementPopupService,
    RemboursementComponent,
    RemboursementDetailComponent,
    RemboursementDialogComponent,
    RemboursementPopupComponent,
    RemboursementDeletePopupComponent,
    RemboursementDeleteDialogComponent,
    remboursementRoute,
    remboursementPopupRoute,
    RemboursementResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...remboursementRoute,
    ...remboursementPopupRoute,
];

@NgModule({
    imports: [
        SenPaieSharedModule,
        SenPaieAdminModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        RemboursementComponent,
        RemboursementDetailComponent,
        RemboursementDialogComponent,
        RemboursementDeleteDialogComponent,
        RemboursementPopupComponent,
        RemboursementDeletePopupComponent,
    ],
    entryComponents: [
        RemboursementComponent,
        RemboursementDialogComponent,
        RemboursementPopupComponent,
        RemboursementDeleteDialogComponent,
        RemboursementDeletePopupComponent,
    ],
    providers: [
        RemboursementService,
        RemboursementPopupService,
        RemboursementResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SenPaieRemboursementModule {}
