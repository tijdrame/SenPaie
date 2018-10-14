import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SenPaieSharedModule } from '../../shared';
import {
    TypeRelationService,
    TypeRelationPopupService,
    TypeRelationComponent,
    TypeRelationDetailComponent,
    TypeRelationDialogComponent,
    TypeRelationPopupComponent,
    TypeRelationDeletePopupComponent,
    TypeRelationDeleteDialogComponent,
    typeRelationRoute,
    typeRelationPopupRoute,
    TypeRelationResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...typeRelationRoute,
    ...typeRelationPopupRoute,
];

@NgModule({
    imports: [
        SenPaieSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        TypeRelationComponent,
        TypeRelationDetailComponent,
        TypeRelationDialogComponent,
        TypeRelationDeleteDialogComponent,
        TypeRelationPopupComponent,
        TypeRelationDeletePopupComponent,
    ],
    entryComponents: [
        TypeRelationComponent,
        TypeRelationDialogComponent,
        TypeRelationPopupComponent,
        TypeRelationDeleteDialogComponent,
        TypeRelationDeletePopupComponent,
    ],
    providers: [
        TypeRelationService,
        TypeRelationPopupService,
        TypeRelationResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SenPaieTypeRelationModule {}
