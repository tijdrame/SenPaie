import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SenPaieSharedModule } from '../../shared';
import {
    RegimeService,
    RegimePopupService,
    RegimeComponent,
    RegimeDetailComponent,
    RegimeDialogComponent,
    RegimePopupComponent,
    RegimeDeletePopupComponent,
    RegimeDeleteDialogComponent,
    regimeRoute,
    regimePopupRoute,
    RegimeResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...regimeRoute,
    ...regimePopupRoute,
];

@NgModule({
    imports: [
        SenPaieSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        RegimeComponent,
        RegimeDetailComponent,
        RegimeDialogComponent,
        RegimeDeleteDialogComponent,
        RegimePopupComponent,
        RegimeDeletePopupComponent,
    ],
    entryComponents: [
        RegimeComponent,
        RegimeDialogComponent,
        RegimePopupComponent,
        RegimeDeleteDialogComponent,
        RegimeDeletePopupComponent,
    ],
    providers: [
        RegimeService,
        RegimePopupService,
        RegimeResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SenPaieRegimeModule {}
