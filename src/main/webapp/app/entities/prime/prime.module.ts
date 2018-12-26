import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SenPaieSharedModule } from '../../shared';
import {
    PrimeService,
    PrimePopupService,
    PrimeComponent,
    PrimeDetailComponent,
    PrimeDialogComponent,
    PrimePopupComponent,
    PrimeDeletePopupComponent,
    PrimeDeleteDialogComponent,
    primeRoute,
    primePopupRoute,
    PrimeResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...primeRoute,
    ...primePopupRoute,
];

@NgModule({
    imports: [
        SenPaieSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        PrimeComponent,
        PrimeDetailComponent,
        PrimeDialogComponent,
        PrimeDeleteDialogComponent,
        PrimePopupComponent,
        PrimeDeletePopupComponent,
    ],
    entryComponents: [
        PrimeComponent,
        PrimeDialogComponent,
        PrimePopupComponent,
        PrimeDeleteDialogComponent,
        PrimeDeletePopupComponent,
    ],
    providers: [
        PrimeService,
        PrimePopupService,
        PrimeResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SenPaiePrimeModule {}
