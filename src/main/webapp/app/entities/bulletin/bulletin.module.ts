import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SenPaieSharedModule } from '../../shared';
import { SenPaieAdminModule } from '../../admin/admin.module';
import {
    BulletinService,
    BulletinPopupService,
    BulletinComponent,
    BulletinDetailComponent,
    BulletinDialogComponent,
    BulletinPopupComponent,
    BulletinDeletePopupComponent,
    BulletinDeleteDialogComponent,
    bulletinRoute,
    bulletinPopupRoute,
    BulletinResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...bulletinRoute,
    ...bulletinPopupRoute,
];

@NgModule({
    imports: [
        SenPaieSharedModule,
        SenPaieAdminModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        BulletinComponent,
        BulletinDetailComponent,
        BulletinDialogComponent,
        BulletinDeleteDialogComponent,
        BulletinPopupComponent,
        BulletinDeletePopupComponent,
    ],
    entryComponents: [
        BulletinComponent,
        BulletinDialogComponent,
        BulletinPopupComponent,
        BulletinDeleteDialogComponent,
        BulletinDeletePopupComponent,
    ],
    providers: [
        BulletinService,
        BulletinPopupService,
        BulletinResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SenPaieBulletinModule {}
