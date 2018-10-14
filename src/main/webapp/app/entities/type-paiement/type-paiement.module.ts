import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SenPaieSharedModule } from '../../shared';
import {
    TypePaiementService,
    TypePaiementPopupService,
    TypePaiementComponent,
    TypePaiementDetailComponent,
    TypePaiementDialogComponent,
    TypePaiementPopupComponent,
    TypePaiementDeletePopupComponent,
    TypePaiementDeleteDialogComponent,
    typePaiementRoute,
    typePaiementPopupRoute,
    TypePaiementResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...typePaiementRoute,
    ...typePaiementPopupRoute,
];

@NgModule({
    imports: [
        SenPaieSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        TypePaiementComponent,
        TypePaiementDetailComponent,
        TypePaiementDialogComponent,
        TypePaiementDeleteDialogComponent,
        TypePaiementPopupComponent,
        TypePaiementDeletePopupComponent,
    ],
    entryComponents: [
        TypePaiementComponent,
        TypePaiementDialogComponent,
        TypePaiementPopupComponent,
        TypePaiementDeleteDialogComponent,
        TypePaiementDeletePopupComponent,
    ],
    providers: [
        TypePaiementService,
        TypePaiementPopupService,
        TypePaiementResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SenPaieTypePaiementModule {}
