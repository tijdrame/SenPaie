import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SenPaieSharedModule } from '../../shared';
import {
    CategorieService,
    CategoriePopupService,
    CategorieComponent,
    CategorieDetailComponent,
    CategorieDialogComponent,
    CategoriePopupComponent,
    CategorieDeletePopupComponent,
    CategorieDeleteDialogComponent,
    categorieRoute,
    categoriePopupRoute,
    CategorieResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...categorieRoute,
    ...categoriePopupRoute,
];

@NgModule({
    imports: [
        SenPaieSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        CategorieComponent,
        CategorieDetailComponent,
        CategorieDialogComponent,
        CategorieDeleteDialogComponent,
        CategoriePopupComponent,
        CategorieDeletePopupComponent,
    ],
    entryComponents: [
        CategorieComponent,
        CategorieDialogComponent,
        CategoriePopupComponent,
        CategorieDeleteDialogComponent,
        CategorieDeletePopupComponent,
    ],
    providers: [
        CategorieService,
        CategoriePopupService,
        CategorieResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SenPaieCategorieModule {}
