import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SenPaieSharedModule } from '../../shared';
import {
    AvantageService,
    AvantagePopupService,
    AvantageComponent,
    AvantageDetailComponent,
    AvantageDialogComponent,
    AvantagePopupComponent,
    AvantageDeletePopupComponent,
    AvantageDeleteDialogComponent,
    avantageRoute,
    avantagePopupRoute,
    AvantageResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...avantageRoute,
    ...avantagePopupRoute,
];

@NgModule({
    imports: [
        SenPaieSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        AvantageComponent,
        AvantageDetailComponent,
        AvantageDialogComponent,
        AvantageDeleteDialogComponent,
        AvantagePopupComponent,
        AvantageDeletePopupComponent,
    ],
    entryComponents: [
        AvantageComponent,
        AvantageDialogComponent,
        AvantagePopupComponent,
        AvantageDeleteDialogComponent,
        AvantageDeletePopupComponent,
    ],
    providers: [
        AvantageService,
        AvantagePopupService,
        AvantageResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SenPaieAvantageModule {}
