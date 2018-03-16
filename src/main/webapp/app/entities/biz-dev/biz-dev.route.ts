import { Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { BizDevComponent } from './biz-dev.component';
import { BizDevDetailComponent } from './biz-dev-detail.component';
import { BizDevPopupComponent } from './biz-dev-dialog.component';
import { BizDevDeletePopupComponent } from './biz-dev-delete-dialog.component';

export const bizDevRoute: Routes = [
    {
        path: 'biz-dev',
        component: BizDevComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'BizDevs'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'biz-dev/:id',
        component: BizDevDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'BizDevs'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const bizDevPopupRoute: Routes = [
    {
        path: 'biz-dev-new',
        component: BizDevPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'BizDevs'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'biz-dev/:id/edit',
        component: BizDevPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'BizDevs'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'biz-dev/:id/delete',
        component: BizDevDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'BizDevs'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
