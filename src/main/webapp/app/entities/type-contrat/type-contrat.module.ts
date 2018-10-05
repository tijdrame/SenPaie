import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SenPaieSharedModule } from '../../shared';
import {
    TypeContratService,
    TypeContratPopupService,
    TypeContratComponent,
    TypeContratDetailComponent,
    TypeContratDialogComponent,
    TypeContratPopupComponent,
    TypeContratDeletePopupComponent,
    TypeContratDeleteDialogComponent,
    typeContratRoute,
    typeContratPopupRoute,
    TypeContratResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...typeContratRoute,
    ...typeContratPopupRoute,
];

@NgModule({
    imports: [
        SenPaieSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        TypeContratComponent,
        TypeContratDetailComponent,
        TypeContratDialogComponent,
        TypeContratDeleteDialogComponent,
        TypeContratPopupComponent,
        TypeContratDeletePopupComponent,
    ],
    entryComponents: [
        TypeContratComponent,
        TypeContratDialogComponent,
        TypeContratPopupComponent,
        TypeContratDeleteDialogComponent,
        TypeContratDeletePopupComponent,
    ],
    providers: [
        TypeContratService,
        TypeContratPopupService,
        TypeContratResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SenPaieTypeContratModule {}
