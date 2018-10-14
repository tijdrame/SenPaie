import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil } from 'ng-jhipster';

import { UserRouteAccessService } from '../../shared';
import { StatutComponent } from './statut.component';
import { StatutDetailComponent } from './statut-detail.component';
import { StatutPopupComponent } from './statut-dialog.component';
import { StatutDeletePopupComponent } from './statut-delete-dialog.component';

@Injectable()
export class StatutResolvePagingParams implements Resolve<any> {

    constructor(private paginationUtil: JhiPaginationUtil) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const page = route.queryParams['page'] ? route.queryParams['page'] : '1';
        const sort = route.queryParams['sort'] ? route.queryParams['sort'] : 'id,asc';
        return {
            page: this.paginationUtil.parsePage(page),
            predicate: this.paginationUtil.parsePredicate(sort),
            ascending: this.paginationUtil.parseAscending(sort)
      };
    }
}

export const statutRoute: Routes = [
    {
        path: 'statut',
        component: StatutComponent,
        resolve: {
            'pagingParams': StatutResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'senPaieApp.statut.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'statut/:id',
        component: StatutDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'senPaieApp.statut.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const statutPopupRoute: Routes = [
    {
        path: 'statut-new',
        component: StatutPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'senPaieApp.statut.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'statut/:id/edit',
        component: StatutPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'senPaieApp.statut.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'statut/:id/delete',
        component: StatutDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'senPaieApp.statut.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
