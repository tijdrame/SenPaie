import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SenPaieSharedModule } from '../../shared';
import {
    NationaliteService,
    NationalitePopupService,
    NationaliteComponent,
    NationaliteDetailComponent,
    NationaliteDialogComponent,
    NationalitePopupComponent,
    NationaliteDeletePopupComponent,
    NationaliteDeleteDialogComponent,
    nationaliteRoute,
    nationalitePopupRoute,
    NationaliteResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...nationaliteRoute,
    ...nationalitePopupRoute,
];

@NgModule({
    imports: [
        SenPaieSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        NationaliteComponent,
        NationaliteDetailComponent,
        NationaliteDialogComponent,
        NationaliteDeleteDialogComponent,
        NationalitePopupComponent,
        NationaliteDeletePopupComponent,
    ],
    entryComponents: [
        NationaliteComponent,
        NationaliteDialogComponent,
        NationalitePopupComponent,
        NationaliteDeleteDialogComponent,
        NationaliteDeletePopupComponent,
    ],
    providers: [
        NationaliteService,
        NationalitePopupService,
        NationaliteResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SenPaieNationaliteModule {}
