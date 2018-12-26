import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SenPaieSharedModule } from '../../shared';
import {
    PrimeCollabService,
    PrimeCollabPopupService,
    PrimeCollabComponent,
    PrimeCollabDetailComponent,
    PrimeCollabDialogComponent,
    PrimeCollabPopupComponent,
    PrimeCollabDeletePopupComponent,
    PrimeCollabDeleteDialogComponent,
    primeCollabRoute,
    primeCollabPopupRoute,
    PrimeCollabResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...primeCollabRoute,
    ...primeCollabPopupRoute,
];

@NgModule({
    imports: [
        SenPaieSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        PrimeCollabComponent,
        PrimeCollabDetailComponent,
        PrimeCollabDialogComponent,
        PrimeCollabDeleteDialogComponent,
        PrimeCollabPopupComponent,
        PrimeCollabDeletePopupComponent,
    ],
    entryComponents: [
        PrimeCollabComponent,
        PrimeCollabDialogComponent,
        PrimeCollabPopupComponent,
        PrimeCollabDeleteDialogComponent,
        PrimeCollabDeletePopupComponent,
    ],
    providers: [
        PrimeCollabService,
        PrimeCollabPopupService,
        PrimeCollabResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SenPaiePrimeCollabModule {}
