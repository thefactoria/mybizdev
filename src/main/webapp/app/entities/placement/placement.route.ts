import { Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { PlacementComponent } from './placement.component';
import { PlacementDetailComponent } from './placement-detail.component';
import { PlacementPopupComponent } from './placement-dialog.component';
import { PlacementDeletePopupComponent } from './placement-delete-dialog.component';
import { PlacementArchivePopupComponent } from './placement-archive-dialog.component';

export const placementRoute: Routes = [
    {
        path: 'placement',
        component: PlacementComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Placements'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'placement/:id',
        component: PlacementDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Placements'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const placementPopupRoute: Routes = [
    {
        path: 'placement-new',
        component: PlacementPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Placements'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'placement/:id/edit',
        component: PlacementPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Placements'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'placement/:id/delete',
        component: PlacementDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Placements'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'placement/:id/archive',
        component: PlacementArchivePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Placements'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
