import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SenPaieSharedModule } from '../../shared';
import {
    FonctionService,
    FonctionPopupService,
    FonctionComponent,
    FonctionDetailComponent,
    FonctionDialogComponent,
    FonctionPopupComponent,
    FonctionDeletePopupComponent,
    FonctionDeleteDialogComponent,
    fonctionRoute,
    fonctionPopupRoute,
    FonctionResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...fonctionRoute,
    ...fonctionPopupRoute,
];

@NgModule({
    imports: [
        SenPaieSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        FonctionComponent,
        FonctionDetailComponent,
        FonctionDialogComponent,
        FonctionDeleteDialogComponent,
        FonctionPopupComponent,
        FonctionDeletePopupComponent,
    ],
    entryComponents: [
        FonctionComponent,
        FonctionDialogComponent,
        FonctionPopupComponent,
        FonctionDeleteDialogComponent,
        FonctionDeletePopupComponent,
    ],
    providers: [
        FonctionService,
        FonctionPopupService,
        FonctionResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SenPaieFonctionModule {}
