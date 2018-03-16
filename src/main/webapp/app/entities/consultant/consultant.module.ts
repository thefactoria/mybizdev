import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { MybizdevSharedModule } from '../../shared';
import {
    ConsultantService,
    ConsultantPopupService,
    ConsultantComponent,
    ConsultantDetailComponent,
    ConsultantDialogComponent,
    ConsultantPopupComponent,
    ConsultantDeletePopupComponent,
    ConsultantDeleteDialogComponent,
    consultantRoute,
    consultantPopupRoute,
    ConsultantResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...consultantRoute,
    ...consultantPopupRoute,
];

@NgModule({
    imports: [
        MybizdevSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        ConsultantComponent,
        ConsultantDetailComponent,
        ConsultantDialogComponent,
        ConsultantDeleteDialogComponent,
        ConsultantPopupComponent,
        ConsultantDeletePopupComponent,
    ],
    entryComponents: [
        ConsultantComponent,
        ConsultantDialogComponent,
        ConsultantPopupComponent,
        ConsultantDeleteDialogComponent,
        ConsultantDeletePopupComponent,
    ],
    providers: [
        ConsultantService,
        ConsultantPopupService,
        ConsultantResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class MybizdevConsultantModule {}
