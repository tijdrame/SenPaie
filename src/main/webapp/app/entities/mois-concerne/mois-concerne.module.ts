import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SenPaieSharedModule } from '../../shared';
import {
    MoisConcerneService,
    MoisConcernePopupService,
    MoisConcerneComponent,
    MoisConcerneDetailComponent,
    MoisConcerneDialogComponent,
    MoisConcernePopupComponent,
    MoisConcerneDeletePopupComponent,
    MoisConcerneDeleteDialogComponent,
    moisConcerneRoute,
    moisConcernePopupRoute,
    MoisConcerneResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...moisConcerneRoute,
    ...moisConcernePopupRoute,
];

@NgModule({
    imports: [
        SenPaieSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        MoisConcerneComponent,
        MoisConcerneDetailComponent,
        MoisConcerneDialogComponent,
        MoisConcerneDeleteDialogComponent,
        MoisConcernePopupComponent,
        MoisConcerneDeletePopupComponent,
    ],
    entryComponents: [
        MoisConcerneComponent,
        MoisConcerneDialogComponent,
        MoisConcernePopupComponent,
        MoisConcerneDeleteDialogComponent,
        MoisConcerneDeletePopupComponent,
    ],
    providers: [
        MoisConcerneService,
        MoisConcernePopupService,
        MoisConcerneResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SenPaieMoisConcerneModule {}
