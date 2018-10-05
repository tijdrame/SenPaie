import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SenPaieSharedModule } from '../../shared';
import {
    StructureService,
    StructurePopupService,
    StructureComponent,
    StructureDetailComponent,
    StructureDialogComponent,
    StructurePopupComponent,
    StructureDeletePopupComponent,
    StructureDeleteDialogComponent,
    structureRoute,
    structurePopupRoute,
    StructureResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...structureRoute,
    ...structurePopupRoute,
];

@NgModule({
    imports: [
        SenPaieSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        StructureComponent,
        StructureDetailComponent,
        StructureDialogComponent,
        StructureDeleteDialogComponent,
        StructurePopupComponent,
        StructureDeletePopupComponent,
    ],
    entryComponents: [
        StructureComponent,
        StructureDialogComponent,
        StructurePopupComponent,
        StructureDeleteDialogComponent,
        StructureDeletePopupComponent,
    ],
    providers: [
        StructureService,
        StructurePopupService,
        StructureResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SenPaieStructureModule {}
