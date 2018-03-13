import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { MybizdevSharedModule } from '../../shared';
import {
    EtatService,
    EtatPopupService,
    EtatComponent,
    EtatDetailComponent,
    EtatDialogComponent,
    EtatPopupComponent,
    EtatDeletePopupComponent,
    EtatDeleteDialogComponent,
    etatRoute,
    etatPopupRoute,
} from './';

const ENTITY_STATES = [
    ...etatRoute,
    ...etatPopupRoute,
];

@NgModule({
    imports: [
        MybizdevSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        EtatComponent,
        EtatDetailComponent,
        EtatDialogComponent,
        EtatDeleteDialogComponent,
        EtatPopupComponent,
        EtatDeletePopupComponent,
    ],
    entryComponents: [
        EtatComponent,
        EtatDialogComponent,
        EtatPopupComponent,
        EtatDeleteDialogComponent,
        EtatDeletePopupComponent,
    ],
    providers: [
        EtatService,
        EtatPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class MybizdevEtatModule {}
