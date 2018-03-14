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
        PlacementPopupComponent,
        PlacementDeletePopupComponent,
    ],
    entryComponents: [
        PlacementComponent,
        PlacementDialogComponent,
        PlacementPopupComponent,
        PlacementDeleteDialogComponent,
        PlacementDeletePopupComponent,
    ],
    providers: [
        PlacementService,
        PlacementPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class MybizdevPlacementModule {}
