import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { MybizdevSharedModule } from '../../shared';
import {
    PlacementService,
    PlacementPopupService,
    PlacementComponent,
    PlacementDetailComponent,
    PlacementDialogComponent,
    PlacementPopupComponent,
    PlacementDeletePopupComponent,
    PlacementDeleteDialogComponent,
    PlacementArchivePopupComponent,
    PlacementArchiveDialogComponent,
    placementRoute,
    placementPopupRoute,
} from './';

const ENTITY_STATES = [
    ...placementRoute,
    ...placementPopupRoute,
];

@NgModule({
    imports: [
        MybizdevSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        PlacementComponent,
        PlacementDetailComponent,
        PlacementDialogComponent,
        PlacementDeleteDialogComponent,
        PlacementArchiveDialogComponent,
        PlacementPopupComponent,
        PlacementDeletePopupComponent,
        PlacementArchivePopupComponent,
    ],
    entryComponents: [
        PlacementComponent,
        PlacementDialogComponent,
        PlacementPopupComponent,
        PlacementDeleteDialogComponent,
        PlacementDeletePopupComponent,
        PlacementArchiveDialogComponent,
        PlacementArchivePopupComponent,
    ],
    providers: [
        PlacementService,
        PlacementPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class MybizdevPlacementModule {}
