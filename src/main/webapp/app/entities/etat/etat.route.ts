import { Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { EtatComponent } from './etat.component';
import { EtatDetailComponent } from './etat-detail.component';
import { EtatPopupComponent } from './etat-dialog.component';
import { EtatDeletePopupComponent } from './etat-delete-dialog.component';

export const etatRoute: Routes = [
    {
        path: 'etat',
        component: EtatComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Etats'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'etat/:id',
        component: EtatDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Etats'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const etatPopupRoute: Routes = [
    {
        path: 'etat-new',
        component: EtatPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Etats'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'etat/:id/edit',
        component: EtatPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Etats'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'etat/:id/delete',
        component: EtatDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Etats'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
