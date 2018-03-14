import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { MybizdevSharedModule } from '../../shared';
import {
    BizDevService,
    BizDevPopupService,
    BizDevComponent,
    BizDevDetailComponent,
    BizDevDialogComponent,
    BizDevPopupComponent,
    BizDevDeletePopupComponent,
    BizDevDeleteDialogComponent,
    bizDevRoute,
    bizDevPopupRoute,
} from './';

const ENTITY_STATES = [
    ...bizDevRoute,
    ...bizDevPopupRoute,
];

@NgModule({
    imports: [
        MybizdevSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        BizDevComponent,
        BizDevDetailComponent,
        BizDevDialogComponent,
        BizDevDeleteDialogComponent,
        BizDevPopupComponent,
        BizDevDeletePopupComponent,
    ],
    entryComponents: [
        BizDevComponent,
        BizDevDialogComponent,
        BizDevPopupComponent,
        BizDevDeleteDialogComponent,
        BizDevDeletePopupComponent,
    ],
    providers: [
        BizDevService,
        BizDevPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class MybizdevBizDevModule {}
