import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SenPaieSharedModule } from '../../shared';
import {
    ConventionService,
    ConventionPopupService,
    ConventionComponent,
    ConventionDetailComponent,
    ConventionDialogComponent,
    ConventionPopupComponent,
    ConventionDeletePopupComponent,
    ConventionDeleteDialogComponent,
    conventionRoute,
    conventionPopupRoute,
    ConventionResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...conventionRoute,
    ...conventionPopupRoute,
];

@NgModule({
    imports: [
        SenPaieSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        ConventionComponent,
        ConventionDetailComponent,
        ConventionDialogComponent,
        ConventionDeleteDialogComponent,
        ConventionPopupComponent,
        ConventionDeletePopupComponent,
    ],
    entryComponents: [
        ConventionComponent,
        ConventionDialogComponent,
        ConventionPopupComponent,
        ConventionDeleteDialogComponent,
        ConventionDeletePopupComponent,
    ],
    providers: [
        ConventionService,
        ConventionPopupService,
        ConventionResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SenPaieConventionModule {}
