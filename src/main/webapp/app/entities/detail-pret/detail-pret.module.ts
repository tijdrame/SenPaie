import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SenPaieSharedModule } from '../../shared';
import { SenPaieAdminModule } from '../../admin/admin.module';
import {
    DetailPretService,
    DetailPretPopupService,
    DetailPretComponent,
    DetailPretDetailComponent,
    DetailPretDialogComponent,
    DetailPretPopupComponent,
    DetailPretDeletePopupComponent,
    DetailPretDeleteDialogComponent,
    detailPretRoute,
    detailPretPopupRoute,
    DetailPretResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...detailPretRoute,
    ...detailPretPopupRoute,
];

@NgModule({
    imports: [
        SenPaieSharedModule,
        SenPaieAdminModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        DetailPretComponent,
        DetailPretDetailComponent,
        DetailPretDialogComponent,
        DetailPretDeleteDialogComponent,
        DetailPretPopupComponent,
        DetailPretDeletePopupComponent,
    ],
    entryComponents: [
        DetailPretComponent,
        DetailPretDialogComponent,
        DetailPretPopupComponent,
        DetailPretDeleteDialogComponent,
        DetailPretDeletePopupComponent,
    ],
    providers: [
        DetailPretService,
        DetailPretPopupService,
        DetailPretResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SenPaieDetailPretModule {}
